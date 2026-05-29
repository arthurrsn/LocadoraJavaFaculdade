# Locadora

[![Status](https://img.shields.io/badge/status-in%20progress-yellow)](#)

Sistema de locadora em **Java POO**, sem frameworks externos, com arquitetura **MVC** e persistencia em arquivo.

## Estrutura

- `model` - entidades de dominio
- `controller` - regras de manipulacao
- `view` - interface de exibicao
- `dal` - acesso a dados em arquivo
- `factory` - criacao de objetos
- `utils` - utilitarios

## Situacao atual

- Estrutura base do projeto criada
- Models principais criados
- Factories iniciais criadas
- CRUD completo: **em desenvolvimento**

## Executar (PowerShell)

```powershell
Set-Location "D:\Projects\Locadora"
if (Test-Path out) { Remove-Item -Recurse -Force out }
New-Item -ItemType Directory -Path out | Out-Null
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out Main
```
