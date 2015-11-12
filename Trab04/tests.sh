#!/bin/bash


for file in ./tests/teste*.txt; do
    echo "Test file $file (nenhuma mensagem é ok):"
    java Parser $file
    echo " "
done