#!/usr/bin/env python3
"""Import an already obtained local PNG for a private source build; never download."""
import argparse
import re
import shutil
import struct
from pathlib import Path


def validate(source, package):
    if not re.fullmatch(r"[A-Za-z0-9_]+(?:\.[A-Za-z0-9_]+)+", package):
        raise ValueError("Use an exact installed package ID, not an app title or path")
    if not source.is_file() or source.stat().st_size > 2 * 1024 * 1024:
        raise ValueError("PNG must exist and be at most 2 MiB")
    header = source.read_bytes()[:24]
    if len(header) != 24 or header[:8] != b"\x89PNG\r\n\x1a\n" or header[12:16] != b"IHDR":
        raise ValueError("Expected a PNG with an IHDR header")
    width, height = struct.unpack(">II", header[16:24])
    if not (1 <= width <= 4096 and 1 <= height <= 4096):
        raise ValueError("Dimensions must be 1..4096 pixels")
    return width, height


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--package", required=True)
    parser.add_argument("--file", required=True, type=Path)
    args = parser.parse_args()
    validate(args.file, args.package)
    target = Path(__file__).resolve().parents[1] / "launcher/res/drawable-nodpi" / ("mono_" + args.package.replace(".", "_") + ".png")
    if target.exists():
        raise SystemExit("Override already exists; review/remove it explicitly before replacing")
    shutil.copyfile(args.file, target)
    print("Imported local override for", args.package, "- rebuild to include it. Check asset terms before distributing.")


if __name__ == "__main__":
    main()
