# Adding an icon: maintainer and AI-agent instructions

1. Identify the app, country, official store link and exact installed package ID. Never invent aliases from a similar app name.
2. Prefer the app's installed icon as the fallback. No art match must hide or block an app.
3. Consult `icons/catalog.json`. Follow source links as references; do not assume a publicly accessible file can be redistributed or modified. Record source revision, retrieval date, applicable asset terms and brand guidance.
4. If permission/provenance is unclear, keep the entry as a source pointer and explain the uncertainty. Never manufacture brand logos with an image generator.
5. Where permitted, prepare a white transparent PNG. Preserve the mark's aspect ratio and check whether removing a box or changing color is allowed. Record all transformations. Avoid arbitrary web searches at runtime.
6. Test readability at the 28dp x 18dp corner size. Test invalid/missing assets and unknown apps; names and launching must continue to work.
7. Submit a reviewed source/provenance change. Keep local personal assets out of commits. Screenshots and source URLs are not a substitute for asset review.
8. A future optional desktop downloader must use an explicit allowlist, pinned versions/hashes and validated PNG output, and clearly distinguish downloading from redistribution rights. No remote code execution, shell commands copied from websites or silent downloads.
