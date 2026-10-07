# Agile AI Assistant

> A Spring Boot--based AI engineering project that demonstrates how
> **Generative AI, Agentic AI, Retrieval-Augmented Generation (RAG),
> embeddings, semantic retrieval, prompt engineering,
> tool/function-style routing, structured output, grounding, and
> guardrails** can work together in an end-to-end enterprise
> application.

## Overview

**Agile AI Assistant** is a learning and showcase project built to
explore production-oriented AI application architecture using Agile
delivery data such as **Features, User Stories, and Defects**.

The application is designed around an important principle:

> **Not every question should be answered by vector search, and not
> every question should be sent directly to an LLM.**

Instead, every user request first passes through an **AI
routing/orchestration layer**. The router determines what kind of
processing is required and sends the request through one of four paths:

-   **STRUCTURED** --- deterministic filtering, lookup, listing, and
    counting.
-   **RAG** --- semantic retrieval using embeddings and vector
    similarity.
-   **HYBRID** --- combines structured constraints with semantic
    retrieval and/or LLM-based summarization.
-   **UNSUPPORTED** --- safely rejects requests that cannot be answered
    from the supported Agile domain.

The project intentionally keeps persistence implementation details
abstract in this README. Agile records are represented as **metadata**,
while semantic representations are stored as **vector embeddings**.

------------------------------------------------------------------------

## What This Project Demonstrates

This project is intended to showcase practical implementation of modern
AI application concepts rather than simply calling an LLM API.

### Generative AI

An LLM is used where natural-language reasoning or response generation
adds value, such as:

-   summarizing Agile records;
-   transforming retrieved context into natural-language answers;
-   formatting grounded data as paragraphs, lists, tables, or other
    requested formats;
-   interpreting user intent during routing.

The LLM is **not treated as the source of truth**. It receives retrieved
Agile context and generates responses from that context.

### Agentic AI

The application uses specialized AI responsibilities rather than one
large prompt that performs every task.

The routing agent analyzes a request and decides:

1.  whether the request is supported;
2.  which Agile entity is involved;
3.  what operation is required;
4.  which structured filters were requested;
5.  whether semantic retrieval is required.

That decision controls which downstream capability is invoked.

### Retrieval-Augmented Generation (RAG)

For semantic questions, the application:

1.  converts the user's semantic search concept into an embedding;
2.  compares it against stored Agile vector embeddings;
3.  retrieves the most semantically relevant records;
4.  constructs grounded context;
5.  sends only that context to the response-generation agent;
6.  generates the final answer.

### Embeddings and Vector Search

Agile content is converted into numeric vector representations.

These embeddings make questions such as:

> Which defects are related to payment failures?

possible even when the exact words in the question do not appear
verbatim in every matching Agile record.

### Prompt Engineering

Agent behavior is externalized into Markdown instruction files instead
of being buried inside Java code.

The prompts define:

-   routing rules;
-   supported entities;
-   allowed operations;
-   valid filters;
-   semantic-search rules;
-   output contracts;
-   grounding requirements;
-   unsupported-query behavior;
-   response-formatting rules.

### Guardrails and Grounding

The generation layer is instructed to use **only supplied Agile
context**.

It must not invent:

-   Feature IDs;
-   User Story IDs;
-   Defect IDs;
-   releases;
-   dates;
-   descriptions;
-   story points;
-   progress statuses;
-   parent/child relationships;
-   other project-specific facts.

When sufficient context is unavailable, the application returns a
controlled insufficient-data response rather than asking the model to
guess.

------------------------------------------------------------------------

# High-Level Architecture

``` mermaid
flowchart TB
    U[User / API Client] --> API[Spring Boot Query API]

    API --> RA[Query Routing Agent]

    RA --> RD{Route Decision}

    RD -->|STRUCTURED| SR[Structured Retrieval]
    RD -->|RAG| RR[RAG Retrieval]
    RD -->|HYBRID| HR[Hybrid Orchestration]
    RD -->|UNSUPPORTED| UR[Controlled Unsupported Response]

    SR --> MD[(Agile Metadata)]

    RR --> QE[Query Embedding]
    QE --> VS[Vector Similarity Search]
    VS --> VE[(Vector Embeddings + Metadata)]
    VE --> RC[RAG Context]

    HR --> MD
    HR --> QE
    HR --> RC

    SR --> GC[Grounded Context]
    RC --> GC
    MD --> GC

    GC --> GA[Response Generation Agent]
    GA --> LLM[LLM / SLM]
    LLM --> GR[Grounded Natural-Language Response]

    GR --> API
    UR --> API

    subgraph Ingestion
        DS[Agile Data Source] --> IN[Ingestion / Normalization]
        IN --> MD
        IN --> EM[Embedding Model]
        EM --> VE
    end
```

------------------------------------------------------------------------

# End-to-End Request Flow

A user request is not sent directly to a generative model.

``` text
User Question
     |
     v
Spring Boot API
     |
     v
Query Routing Agent
     |
     +--------------------+--------------------+--------------------+
     |                    |                    |                    |
     v                    v                    v                    v
STRUCTURED               RAG                 HYBRID            UNSUPPORTED
     |                    |                    |                    |
Metadata lookup      Query embedding      Metadata +         Controlled
/filter/count        + vector search      semantic/generative rejection
     |                    |               processing             |
     +--------------------+---------+----------+                  |
                                  |
                                  v
                           Grounded Context
                                  |
                                  v
                       Response Generation Agent
                                  |
                                  v
                              LLM / SLM
                                  |
                                  v
                           Final Response
```

------------------------------------------------------------------------

# Core Domain

The current assistant operates on three Agile entity types:

  Entity         Purpose
  -------------- -----------------------------------------------
  `FEATURE`      Higher-level Agile feature/release capability
  `USER_STORY`   Implementable work associated with a feature
  `DEFECT`       Defect/issue associated with Agile delivery

The design is intentionally domain-aware. The router does not simply
classify a request as "search" or "chat"; it produces a structured
decision describing what the application should do.

------------------------------------------------------------------------

# AI Routing and Orchestration

## Why a Router?

Consider these questions:

``` text
How many user stories are in release 2026.04?
```

``` text
Which defects are related to payment failures?
```

``` text
Which defects related to payment failures are in release 2026.04?
```

``` text
Summarize all features in release 2026.04.
```

All four questions concern Agile data, but they require different
execution strategies.

Running vector similarity search for the first question would be
unnecessary and potentially inaccurate.

Running only exact metadata filters for the second question would lose
semantic meaning.

The router therefore converts natural language into an explicit
machine-readable execution decision.

## Route Decision Contract

Conceptually, the routing agent produces:

``` json
{
  "intent": "STRUCTURED | RAG | HYBRID | UNSUPPORTED",
  "entityType": "FEATURE | USER_STORY | DEFECT | null",
  "operation": "COUNT | GET | LIST | SEARCH | SUMMARY | null",
  "filters": {},
  "searchText": null
}
```

This structured output becomes the contract between the AI routing layer
and deterministic application code.

## Supported Operations

  Operation   Purpose
  ----------- ----------------------------------------------------
  `COUNT`     Count records matching structured criteria
  `GET`       Retrieve specific record IDs
  `LIST`      List records selected using structured metadata
  `SEARCH`    Perform semantic retrieval
  `SUMMARY`   Generate a grounded summary of selected Agile data

------------------------------------------------------------------------

# Route 1 --- STRUCTURED

`STRUCTURED` is used when the answer can be determined from exact Agile
metadata.

Examples:

``` text
How many features are there?
```

``` text
Give me features F10007, F10008 and F10009.
```

``` text
Give me user stories for features F10007 and F10008.
```

A route decision may look like:

``` json
{
  "intent": "STRUCTURED",
  "entityType": "FEATURE",
  "operation": "GET",
  "filters": {
    "featureNumber": ["F10007", "F10008", "F10009"]
  },
  "searchText": null
}
```

### Execution

``` text
Question
   |
   v
Routing Agent
   |
   v
STRUCTURED
   |
   v
Extract entity + operation + filters
   |
   v
Deterministic metadata retrieval
   |
   v
Matching records / calculated result
   |
   v
Response
```

The important design decision is that **vector search is not used for
deterministic questions**.

If a user asks for an exact count, exact IDs, exact release, or other
structured property, the application uses authoritative metadata.

------------------------------------------------------------------------

# Route 2 --- RAG

`RAG` is used when the user asks a semantic question requiring
understanding of Agile headings, names, or descriptions.

Example:

``` text
Which defects are related to payment failures?
```

Router output:

``` json
{
  "intent": "RAG",
  "entityType": "DEFECT",
  "operation": "SEARCH",
  "filters": {},
  "searchText": "payment failures"
}
```

Notice that `searchText` contains only the **semantic concept**.

Words such as:

-   show;
-   list;
-   summarize;
-   table;
-   paragraph;
-   detailed;

are not treated as semantic search concepts.

## RAG Pipeline

``` mermaid
sequenceDiagram
    participant U as User
    participant R as Routing Agent
    participant E as Embedding Model
    participant V as Vector Retrieval
    participant G as Generation Agent
    participant L as LLM/SLM

    U->>R: Which defects are related to payment failures?
    R-->>R: intent=RAG, entity=DEFECT
    R->>E: Embed "payment failures"
    E-->>V: Query vector
    V->>V: Similarity search against stored embeddings
    V-->>G: Relevant defect metadata/content
    G->>L: User question + retrieved Agile context + grounding rules
    L-->>G: Grounded answer
    G-->>U: Final response
```

## Retrieval-Augmented Generation Steps

### 1. Semantic query extraction

The router separates semantic meaning from structured constraints.

``` text
User question:
Which defects are related to payment failures?

Semantic search text:
payment failures
```

### 2. Query embedding

The semantic text is passed through the same compatible embedding model
used to represent indexed Agile content.

Conceptually:

``` text
"payment failures"
        |
        v
Embedding Model
        |
        v
[0.021, -0.114, 0.338, ...]
```

### 3. Similarity search

The query vector is compared with stored vectors.

A similarity metric such as cosine similarity can be used:

``` text
similarity(queryVector, storedVector)
```

The most relevant records are selected using Top-K retrieval.

### 4. Metadata association

Each vector remains associated with Agile metadata so the retrieved
semantic result can still preserve authoritative information such as:

``` text
entity type
entity identifier
release
parent relationship
progress
story/defect points
other supported structured attributes
```

### 5. Context construction

The retrieved records are transformed into a bounded context payload.

### 6. Grounded generation

The response-generation agent receives:

``` text
SYSTEM / AGENT INSTRUCTIONS
+
USER QUESTION
+
RETRIEVED AGILE CONTEXT
```

The model is explicitly instructed not to invent information outside
that context.

------------------------------------------------------------------------

# Route 3 --- HYBRID

`HYBRID` handles questions requiring more than one processing technique.

There are two major hybrid patterns.

## Pattern A --- Structured Filtering + Semantic Retrieval

Example:

``` text
Which defects related to payment failures are in releases 2026.04 and 2026.05?
```

Router output:

``` json
{
  "intent": "HYBRID",
  "entityType": "DEFECT",
  "operation": "SEARCH",
  "filters": {
    "release": ["2026.04", "2026.05"]
  },
  "searchText": "payment failures"
}
```

The application now has two independent concepts:

``` text
Structured constraint:
release IN [2026.04, 2026.05]

Semantic concept:
payment failures
```

The system combines those signals rather than forcing both into either
structured filtering or vector similarity alone.

## Pattern B --- Structured Retrieval + Generative Summarization

Example:

``` text
Summarize all features in release 2026.04.
```

Router output:

``` json
{
  "intent": "HYBRID",
  "entityType": "FEATURE",
  "operation": "SUMMARY",
  "filters": {
    "release": ["2026.04"]
  },
  "searchText": null
}
```

This is a critical design distinction.

The word **summarize** does **not** automatically mean vector search.

The requested dataset is already deterministic:

``` text
ALL FEATURES WHERE release = 2026.04
```

Therefore the system:

1.  retrieves the complete structured dataset;
2.  constructs context from those records;
3.  asks the LLM to summarize the selected records.

This prevents a Top-K semantic search from accidentally omitting records
that belong to the requested release.

------------------------------------------------------------------------

# Route 4 --- UNSUPPORTED

The assistant is intentionally domain bounded.

Examples:

``` text
What is the weather today?
```

``` text
What is the stock price of Apple?
```

``` text
Book me a flight.
```

These requests produce:

``` json
{
  "intent": "UNSUPPORTED",
  "entityType": null,
  "operation": null,
  "filters": {},
  "searchText": null
}
```

The application then returns a controlled response rather than allowing
the model to answer from general knowledge.

This guardrail is important because the application is intended to
answer **Agile-domain questions**, not behave as an unrestricted
general-purpose chatbot.

------------------------------------------------------------------------

# Structured Filters vs Semantic Search

A core architectural rule is:

> **Structured facts belong in filters. Semantic concepts belong in
> `searchText`.**

Example:

``` text
Which features related to flight disruptions are in releases 2026.03 and 2026.04?
```

Correct:

``` json
{
  "intent": "HYBRID",
  "entityType": "FEATURE",
  "operation": "SEARCH",
  "filters": {
    "release": ["2026.03", "2026.04"]
  },
  "searchText": "flight disruptions"
}
```

The release numbers are deterministic constraints.

`flight disruptions` expresses semantic meaning.

Keeping these separate makes downstream execution explicit, testable,
and less dependent on model behavior.

------------------------------------------------------------------------

# Agent Design

The project separates AI responsibilities into specialized instruction
sets.

A simplified conceptual design is:

``` text
agents/
├── query-agent.md
└── rag-generation.md
```

## Query Routing Agent

The routing agent does **not** answer the user's Agile question.

Its responsibility is to produce a route decision.

It determines:

-   intent;
-   entity type;
-   operation;
-   structured filters;
-   semantic search text.

The routing prompt also defines strict business rules such as:

-   allowed filter names;
-   multi-value filter handling;
-   release-summary behavior;
-   semantic-search extraction;
-   supported/unsupported boundaries;
-   output JSON contract.

This makes the LLM act as an **intent/orchestration component**, not as
an unrestricted answer generator.

## Response Generation Agent

The response-generation agent receives already-retrieved Agile context.

Its responsibility is to:

-   answer using only supplied context;
-   preserve authoritative metadata;
-   summarize when requested;
-   honor requested presentation format;
-   avoid inventing missing facts.

Separating routing from generation reduces prompt complexity and makes
each AI stage easier to test.

------------------------------------------------------------------------

# Tool / Function-Style Execution

The routing decision behaves like a tool-selection plan.

For example:

``` text
User
  |
  v
"How many user stories are in release 2026.04?"
  |
  v
Routing Agent
  |
  v
{
  intent: STRUCTURED,
  entityType: USER_STORY,
  operation: COUNT,
  filters: { release: ["2026.04"] }
}
  |
  v
Application selects structured retrieval capability
  |
  v
COUNT matching USER_STORY records
```

For semantic retrieval:

``` text
User
  |
  v
"Which defects are related to payment failures?"
  |
  v
Routing Agent
  |
  v
{
  intent: RAG,
  entityType: DEFECT,
  operation: SEARCH,
  searchText: "payment failures"
}
  |
  v
Application selects semantic retrieval capability
  |
  v
Embedding -> Similarity Search -> Context -> Generation
```

This is the foundation of the project's **agentic workflow**: the model
helps decide which capability should execute, while application code
controls the actual execution.

------------------------------------------------------------------------

# Data Ingestion and Embedding Pipeline

AI retrieval quality depends heavily on ingestion quality.

The ingestion pipeline prepares Agile data before user queries are
processed.

``` mermaid
flowchart LR
    A[Agile Data Source] --> B[Fetch Records]
    B --> C[Normalize]
    C --> D[Feature / User Story / Defect Metadata]
    D --> M[(Metadata Store)]
    D --> T[Construct Embedding Text]
    T --> E[Embedding Model]
    E --> V[Vector Embedding]
    V --> X[(Vector Embeddings + Metadata)]
```

## Ingestion Responsibilities

The pipeline is responsible for:

1.  retrieving Agile records;
2.  normalizing supported entity fields;
3.  maintaining stable entity identifiers;
4.  storing authoritative metadata;
5.  constructing text suitable for semantic retrieval;
6.  generating embeddings;
7.  associating vectors with the correct entity metadata;
8.  updating semantic representations when source content changes.

## Example Embedding Document

A Feature can be converted into a semantic document conceptually similar
to:

``` text
Entity: FEATURE
Feature Number: F10007
Heading: Real-time Flight Status and Notifications
Description: Provide real-time flight status including departure...
Release: 2026.04
```

The textual representation is converted into an embedding.

The structured metadata remains available independently.

This allows the application to support both:

``` text
release = 2026.04
```

and:

``` text
"features related to flight notifications"
```

without confusing the two retrieval mechanisms.

------------------------------------------------------------------------

# Metadata + Vector Embedding Model

Conceptually, each indexed semantic record contains:

``` json
{
  "id": "F10007",
  "entityType": "FEATURE",
  "metadata": {
    "featureNumber": "F10007",
    "release": "2026.04"
  },
  "content": "Real-time Flight Status and Notifications ...",
  "embedding": [0.021, -0.114, 0.338]
}
```

The exact persistence technology is intentionally not part of the
architecture contract.

The important design is the separation of:

``` text
Authoritative metadata
        +
Semantic content
        +
Vector embedding
```

------------------------------------------------------------------------

# Scheduled Synchronization

Agile data changes over time.

The project is designed to support a scheduled synchronization process
that refreshes Agile metadata and semantic representations.

Conceptually:

``` text
12:00 AM
   |
   v
Scheduled Ingestion
   |
   v
Fetch latest Agile records
   |
   v
Normalize / compare
   |
   +---- unchanged ----> keep current representation
   |
   +---- changed ------> update metadata
                           |
                           v
                    regenerate embedding
                           |
                           v
                    replace vector representation
```

A production-oriented implementation should avoid regenerating every
embedding when nothing changed. Stable IDs and content comparison allow
changed records to be selectively re-embedded.

------------------------------------------------------------------------

# Prompt Engineering

Prompt engineering is treated as application configuration and behavior
design rather than ad-hoc strings scattered throughout Java code.

## Routing Prompt Responsibilities

The routing instructions define:

``` text
Supported intents
Supported entities
Supported operations
Valid filters
Filter extraction rules
Semantic search rules
Release summary rules
Unsupported-domain rules
Structured JSON output contract
```

## Generation Prompt Responsibilities

The generation instructions define:

``` text
Use only supplied Agile context
Never invent Agile records
Preserve record metadata
Honor requested response format
Return insufficient-data response when context is insufficient
```

This separation allows prompts to evolve without tightly coupling every
behavioral change to Java orchestration code.

------------------------------------------------------------------------

# Hallucination Prevention

Hallucination prevention is implemented at multiple layers rather than
relying on a single sentence in a prompt.

## Layer 1 --- Domain Boundary

Out-of-domain questions are routed to `UNSUPPORTED`.

## Layer 2 --- Deterministic Retrieval

Exact questions use authoritative metadata rather than semantic
approximation.

## Layer 3 --- Bounded Semantic Retrieval

RAG supplies only relevant Agile records to the generation model.

## Layer 4 --- Grounded Generation Instructions

The response-generation agent is explicitly prohibited from inventing
project-specific facts.

## Layer 5 --- Authoritative Metadata Preservation

Fields attached to retrieved records remain authoritative.

For example, the model must not infer that a Feature belongs to another
release based on narrative similarity or neighboring records.

## Layer 6 --- Insufficient Context Handling

If the retrieved context does not support an answer, the assistant
reports that the available Agile data is insufficient.

------------------------------------------------------------------------

# Why Not Send Every Question to RAG?

This is one of the most important architectural lessons demonstrated by
the project.

Suppose release `2026.04` contains ten Features.

The user asks:

``` text
Summarize all features in release 2026.04.
```

If semantic retrieval returns only Top-K = 3, seven valid Features may
be excluded.

The generated summary would then be fluent but incomplete.

Instead:

``` text
Structured filter
release = 2026.04
        |
        v
Retrieve ALL matching Features
        |
        v
Send complete dataset to generation agent
        |
        v
Grounded release summary
```

Vector retrieval is used when **semantic similarity is the selection
criterion**.

Structured retrieval is used when **the dataset can be selected
deterministically**.

Hybrid orchestration combines them only when the question actually
requires both.

------------------------------------------------------------------------

# Response Formatting

Presentation is intentionally separated from routing.

These requests can all have the same underlying data route:

``` text
Give me defects in release 2026.04.
```

``` text
Give me defects in release 2026.04 in a table.
```

``` text
Give me defects in release 2026.04 as bullet points.
```

The requested presentation format does not change whether the data is
structured or semantic.

The generation layer handles:

-   Markdown tables;
-   paragraphs;
-   bullet lists;
-   numbered lists;
-   concise answers;
-   detailed answers;
-   selected output columns.

This keeps retrieval decisions based on **data semantics**, not UI
formatting.

------------------------------------------------------------------------

# Example Queries

## Structured Count

``` text
How many user stories are in releases 2026.04 and 2026.05?
```

Expected route:

``` text
STRUCTURED
USER_STORY
COUNT
release = [2026.04, 2026.05]
```

## Structured Lookup

``` text
Give me features F10007, F10008 and F10009.
```

Expected route:

``` text
STRUCTURED
FEATURE
GET
featureNumber = [F10007, F10008, F10009]
```

## Semantic RAG

``` text
Which defects are related to payment failures?
```

Expected route:

``` text
RAG
DEFECT
SEARCH
searchText = "payment failures"
```

## Semantic + Structured Hybrid

``` text
Which defects related to payment failures are in release 2026.04?
```

Expected route:

``` text
HYBRID
DEFECT
SEARCH
release = [2026.04]
searchText = "payment failures"
```

## Structured + Generative Hybrid

``` text
Summarize features in releases 2026.04 and 2026.05.
```

Expected route:

``` text
HYBRID
FEATURE
SUMMARY
release = [2026.04, 2026.05]
searchText = null
```

## Unsupported

``` text
What is the weather today?
```

Expected route:

``` text
UNSUPPORTED
```

------------------------------------------------------------------------

# Technology Stack

  Area                   Technology / Approach
  ---------------------- -----------------------------------------------------
  Application            Java, Spring Boot
  AI Integration         Spring AI
  Generative AI          LLM / SLM
  Agentic AI             Intent routing, specialized agents, orchestration
  RAG                    Retrieval-Augmented Generation
  Embeddings             Local/compatible embedding model
  Semantic Retrieval     Vector embeddings + similarity search
  Structured Retrieval   Agile metadata filtering
  Prompting              Externalized Markdown agent instructions
  AI Output Control      Structured route decision
  Guardrails             Domain restriction, grounding, unsupported handling
  API                    REST
  Domain                 Features, User Stories, Defects

------------------------------------------------------------------------

# Project Structure

The project uses the Java base package:

``` text
com.example.rally
```

and keeps agent-related Java components under:

``` text
com.example.rally.agents
```

A representative organization is:

``` text
src/
└── main/
    ├── java/
    │   └── com/example/rally/
    │       ├── controller/
    │       ├── dto/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── agents/
    │
    └── resources/
        ├── agents/
        │   ├── query-agent.md
        │   └── rag-generation.md
        └── application.yml
```

The exact class layout may evolve as the learning project grows, while
the architectural boundaries remain the same.

------------------------------------------------------------------------

# Query API

The assistant exposes a query endpoint through the Spring Boot
application.

Conceptually:

``` http
POST /api/query
Content-Type: application/json
```

Example request:

``` json
{
  "question": "Which defects related to payment failures are in release 2026.04?"
}
```

Internal execution:

``` text
POST /api/query
      |
      v
Query Controller
      |
      v
AI Orchestration Service
      |
      v
Query Routing Agent
      |
      v
HYBRID route
      |
      +--> release metadata filter
      |
      +--> semantic embedding/search
      |
      v
Grounded Context
      |
      v
Response Generation Agent
      |
      v
Response
```

------------------------------------------------------------------------

# Design Principles

## 1. Use AI only where AI adds value

Counts and exact lookups should remain deterministic.

## 2. Keep the LLM away from source-of-truth responsibilities

The model generates language; the application supplies facts.

## 3. Separate routing from generation

Classification/orchestration and answer generation are different
responsibilities.

## 4. Separate metadata from semantic meaning

Metadata supports deterministic filtering.

Embeddings support semantic similarity.

## 5. Make agent behavior explicit

Externalized instruction files document what each agent is allowed to
do.

## 6. Prefer structured AI output for orchestration

The router produces a machine-readable execution contract instead of
free-form prose.

## 7. Ground every generated Agile answer

Generation happens after retrieval, not instead of retrieval.

## 8. Fail safely

Unsupported questions and insufficient context produce controlled
responses.

------------------------------------------------------------------------

# AI Concepts Implemented

This project demonstrates the following AI engineering concepts end to
end:

-   **Generative AI**
-   **LLM/SLM integration**
-   **Agentic AI**
-   **AI agents**
-   **Intent classification**
-   **AI orchestration**
-   **Structured model output**
-   **Tool/function-style routing**
-   **Retrieval-Augmented Generation (RAG)**
-   **Embeddings**
-   **Vector search**
-   **Semantic retrieval**
-   **Top-K similarity retrieval**
-   **Metadata filtering**
-   **Hybrid retrieval**
-   **Prompt engineering**
-   **Externalized agent instructions**
-   **Context construction**
-   **Grounded generation**
-   **Hallucination guardrails**
-   **Unsupported-query handling**
-   **Deterministic vs semantic retrieval**
-   **Scheduled data ingestion**
-   **Incremental embedding refresh**

------------------------------------------------------------------------

# Key Learning

The central lesson of Agile AI Assistant is that building an enterprise
AI application is not simply:

``` text
Question -> LLM -> Answer
```

A more reliable architecture is:

``` text
Question
   |
   v
Understand Intent
   |
   v
Choose the Correct Retrieval / Execution Strategy
   |
   +--> Structured
   +--> Semantic RAG
   +--> Hybrid
   +--> Unsupported
   |
   v
Retrieve Authoritative Context
   |
   v
Apply Grounding + Guardrails
   |
   v
Generate the Response
```

The LLM is one component of the system---not the entire system.

------------------------------------------------------------------------

# Future Enhancements

Potential extensions to the learning project include:

-   additional Agile entity types;
-   richer metadata filters;
-   configurable retrieval thresholds and Top-K values;
-   reranking of semantic results;
-   retrieval quality evaluation;
-   prompt/version evaluation;
-   conversation-aware follow-up queries;
-   response citations back to Agile records;
-   observability for route decisions, retrieval scores, and generation
    latency;
-   automated evaluation datasets for routing and RAG quality;
-   additional tool integrations through the same agentic orchestration
    model.

------------------------------------------------------------------------

# Disclaimer

This repository is a **standalone learning and portfolio project**
demonstrating AI application architecture with sample Agile-domain data.

It is designed to showcase architectural patterns and implementation
concepts around Spring Boot, Generative AI, Agentic AI, RAG, embeddings,
semantic retrieval, prompting, orchestration, grounding, and guardrails.

------------------------------------------------------------------------

## Author

**Raghunandan Aswathanarayana**

Senior Software Engineer focused on Java, Spring Boot, distributed
systems, cloud-native architecture, and AI-enabled enterprise
applications.
