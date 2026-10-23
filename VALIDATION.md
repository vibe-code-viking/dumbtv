# Local validation

- Java compilation against Android API 25 with JDK 17, warnings treated as errors: passed.
- Android resource compilation/linking, D8 dex generation, ZIP alignment: passed.
- Catalog JSON parsing: passed.
- Snapshot privacy check for known household addresses/identifiers and credential/signing file types: passed.
- Confirmed no service-logo drawable resources or SVG masters are included.
- Default release download blocked until a Viking release is configured.

Not yet validated: activation/restore as the Home handler, runtime icon import under Fire OS storage restrictions, and upstream shell integration tests. The installed personal TV build has not been replaced.

Isolated on-device test (separate package, no Home interception): app discovery, initial Charcoal Smoke, Brand Fades -> Monochrome, background -> Black, persistence after force-stop/restart passed. Local importer header/dimension/package validation passed. Newly added build-time resource lookup compiles; public signed release and fresh activation/restore still pending.

Upstream release CI is not enabled for this source preview: it assumes the upstream release/checksum. Fork-specific automated validation remains pending. Catalog validation passed the local one-level check; deep YAML validation remains pending.
