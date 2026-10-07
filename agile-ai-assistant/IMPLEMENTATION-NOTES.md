# Complete Feature / User Story / Defect implementation

## Bulk ingestion APIs
- POST /api/features -> JSON array of FeatureRequest
- POST /api/user-stories -> JSON array of UserStoryRequest
- POST /api/defects -> JSON array of DefectRequest

Each controller loops through the request list and calls the entity service save method. Each save writes metadata and generates a semantic embedding.

## Generated data files
Under rally.data-dir:
- metadata/features.json
- metadata/user-stories.json
- metadata/defects.json
- vectors/feature-vectors.json
- vectors/user-story-vectors.json
- vectors/defect-vectors.json

## Query API
POST /api/query with the raw natural-language question.

All three entities support:
- STRUCTURED: COUNT, GET, LIST
- RAG: semantic SEARCH/SUMMARY routing with Top-3 retrieval and grounded generation
- HYBRID: exact metadata filters applied before semantic Top-K retrieval
- UNSUPPORTED: handled by QueryService

### Feature structured filters
release, featureSize, epicNumber; GET by featureNumber.

### User Story structured filters
release, featureNumber, storyPoints, userStoryProgress; GET by userStoryNumber.

### Defect structured filters
release, userStoryNumber, defectPoints, defectProgress; GET by defectNumber.

## Sample test questions
Features:
- How many features are in release 2026.03?
- Which features are related to flight disruption?
- Which payment-related features are in release 2026.02?

User stories:
- Get user story US20007
- List user stories under feature F10003
- Which user stories are related to baggage notifications?
- Which payment-related user stories are under F10003?

Defects:
- Get defect DE30007
- How many defects are in release 2026.02?
- Which defects are related to duplicate payment?
- Which payment-related defects are in release 2026.02?

## Environment note
The supplied application.properties retains the original local Windows paths and Ollama model. Update rally.data-dir and model-cache if your local paths differ. Ollama must be running with the configured model for query routing and RAG generation.

## Final query improvements
- RouteDecision filters are now `Map<String, List<String>>`; every query-agent filter value is an array, including single values.
- Multiple releases, feature IDs, user-story IDs, defect IDs, parent IDs, sizes/points and progress values are supported with OR semantics within a field.
- STRUCTURED results are passed through the grounded response generator so user-requested presentation (Markdown table, bullets, paragraph, JSON, etc.) is honored instead of always returning raw JSON.
- Release summaries with exact filters and no semantic concept use HYBRID without vector search and include all matching records (no Top-K truncation).
- User-story release summaries compute total story points in Java and provide the deterministic total to the generator.
- Unsupported/malformed query-agent responses return a controlled 422 QUERY_NOT_SUPPORTED response instead of an unhandled 500.
