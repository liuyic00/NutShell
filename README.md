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

### Install Dependency

Install btormc:

```shell
git clone https://github.com/Boolector/boolector.git
cd boolector
./contrib/setup-lingeling.sh
./contrib/setup-btor2tools.sh
./configure.sh && cd build && make -j$(nproc)
sudo make install
cd ../..
```

### Initialize This Project

```shell
git clone https://github.com/iscas-tis/nutshell-fv
cd nutshell-fv
git submodule update --init --recursive
```

[riscv-spec-core](https://github.com/iscas-tis/riscv-spec-core) has been
imported as a submodule.

### Run Verification

In this project, run:

```shell
mill chiselModule.test.testOnly formal.NutCoreFormalSpec
```

This will run the test case `formal.NutCoreFormalSpec`, which transforms NutCore
(core computing unit) with assertions and `SpecCore` in riscv-spec-core to a
transaction system and then passes it to the formal verification backend.

A bug has been inserted in the ALU.
`adderRes` will give an incorrect value.

## Modifications On NutShell

Search `Formal` in source code to see the main modifications.
