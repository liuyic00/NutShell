# NutShell Formal Verification

This project is a case study using
[riscv-spec-core](https://github.com/iscas-tis/riscv-spec-core) on Nutshell for
formal verification.

## NutShell (果壳)

[NutShell](https://github.com/OSCPU/NutShell) is a processor developed by the
OSCPU (Open Source Chip Project by University) team.
Currently, it supports riscv64/32.
More information about NutShell see its
[GitHub repo](https://github.com/OSCPU/NutShell).

## Run Verification Directly

Clone submodule:

```shell
git submodule update --init --recursive
```

Run verification:

```shell
mill "chiselModule[3.6.0]".test.testOnly formal.NutCoreFormalSpec
```

This will run the test case `formal.NutCoreFormalSpec`, which transforms NutCore
(core computing unit) with assertions and `SpecCore` in riscv-spec-core to a
transaction system and then passes it to the formal verification backend.

## Modifications On NutShell

Search `Formal` in source code to see the main modifications.
