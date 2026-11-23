# Modrinth release details

## Current status

Draft created: https://modrinth.com/project/litematica-jade-bridge/settings

**Do not submit this project for public review in its current form.** Modrinth rejected the AI-generated icon. Its Content Rules §6.2 also prohibit publishing projects whose contents are primarily or entirely AI output; this project's main code and current icon were generated with AI. The remaining fields below are prepared metadata, not a claim that the project has been published or approved.

Policy: https://modrinth.com/legal/rules#generative-ai

## Prepared metadata

- Project name: Litematica Jade Bridge
- Suggested slug: litematica-jade-bridge
- Summary: Show Litematica schematic blocks in Jade with localized names, icons and materials.
- Author: hanlax093
- License: MIT
- Project type: Mod
- Loader: Fabric
- Game version: 26.3
- Client: required; server: unsupported
- Categories: utility
- Source: https://github.com/HanLax93/litematica-jade-bridge
- Issues: https://github.com/HanLax93/litematica-jade-bridge/issues
- Description: `modrinth-description.md`
- Icon: `../src/main/resources/assets/litematica_jade_bridge/icon.png`
- Version: 0.1.0, release
- Primary file: `../build/libs/litematica-jade-bridge-mc26.3-0.1.0.jar`

Mark Jade, Litematica, MaLiLib and Fabric API as **required** dependencies. The bridge JAR contains its own icon but does not bundle any dependency mods.

`modrinth-version.json` contains the prepared version metadata. When using the Modrinth API, add the newly created project's `project_id` and send the primary JAR as the `file` multipart part alongside the JSON `data` part. No credentials are stored in this repository.
