{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
  shellHook = "echo Activated the dev shell";
  packages = with pkgs; [ 
    nodejs_23
    jdk23
    maven
  ];
  fromInputs = [ ];

  ENV_VAR_TEST = "TEST";
}
