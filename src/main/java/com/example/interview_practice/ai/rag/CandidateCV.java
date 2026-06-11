package com.example.interview_practice.ai.rag;


import java.util.List;

public record CandidateCV(
        String candidateName,
        List<Experience> experiences
) {}
