package com.example.interview_practice.ai.rag;

import java.util.List;

public record Experience(
        String jobTitle,
        String companyName,
        String jobDescription,
        String date,
        List<String> skills
) {}
