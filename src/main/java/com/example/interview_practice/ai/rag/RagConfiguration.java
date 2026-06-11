package com.example.interview_practice.ai.rag;

import lombok.extern.java.Log;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log
@Configuration
public class RagConfiguration {

    @Value("vectorstore.json")
    private String vectorStoreName;

    /*
      "classpath:/data/models.json"  →  looks inside src/main/resources/data/models.json
      "file:/tmp/models.json"        →  looks on the filesystem
      "https://example.com/data"     →  fetches from a URL
     */
    @Value("classpath:/ai/data/models.json")
    private Resource models;

    @Bean
    SimpleVectorStore simpleVectorStore(
            // it says how to convert text to vector
            EmbeddingModel embeddingModel) throws IOException {
        var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vectorStoreFile = getVectorStoreFile();
        if (vectorStoreFile.exists()) {
            log.info("Vector Store File Exists,");
            simpleVectorStore.load(vectorStoreFile);
        } else {
            log.info("Vector Store File Does Not Exist, loading documents");
            TextReader textReader = new TextReader(models);
            /*
              Attaches metadata to every document chunk that comes from this file.
               This is useful later when you want to know where a retrieved chunk came from — like citing your source.
             */
            textReader.getCustomMetadata().put("filename", "candidate-cv.txt");
            /*
               Document is a Spring AI class that wraps a piece of text together with its metadata.
               It's not just a raw string — it carries extra information alongside the content:
             */
            List<Document> documents = textReader.get();
            /*
              This is the key step. One big document is useless for RAG — you can't embed a whole file and expect meaningful search results.
               The splitter breaks it into smaller chunks. Smaller chunks = more precise similarity matches when someone asks a question.
             */
            TextSplitter textSplitter = TokenTextSplitter.builder().build();
            List<Document> splitDocuments = textSplitter.apply(documents);
            /*
              This is where the embedding model kicks in. For each chunk, Spring AI calls Ollama's embedding model (nomic-embed-text)
               which converts the text into a vector (a list of numbers that represents the meaning of the text)
             */
            simpleVectorStore.add(splitDocuments);
            // These vectors are stored in the SimpleVectorStore in memory.
            simpleVectorStore.save(vectorStoreFile);
            log.info("Vector file has been created");
        }
        return simpleVectorStore;
    }

    private File getVectorStoreFile() {
        Path path = Paths.get("src", "main", "resources", "ai", "data");
        String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
        return new File(absolutePath);
    }
}
