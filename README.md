# Locadora - Estrutura Inicial (Java POO + MVC)

Estrutura base do sistema de locadora usando Java puro (sem frameworks), com separacao em camadas:

- `model`: classes de dominio
- `controller`: manipulacao de dados
- `view`: visualizacao
- `dal`: acesso a arquivo (banco de dados em arquivo)
- `factory`: fabrica de objetos das classes de dominio
- `utils`: utilitarios

## Estrutura de pastas

```text
src/
  Main.java
  br/com/locadora/
    model/
      Usuario.java
      Item.java
      Filme.java
      Jogo.java
      Locacao.java
    factory/
      UsuarioFactory.java
      FilmeFactory.java
      JogoFactory.java
      LocacaoFactory.java
      package-info.java
    controller/
      package-info.java
    view/
      package-info.java
    dal/
      package-info.java
    utils/
      package-info.java
```

## Como executar (PowerShell)

```powershell
Set-Location "D:\Projects\Locadora"
if (Test-Path out) { Remove-Item -Recurse -Force out }
New-Item -ItemType Directory -Path out | Out-Null
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out Main
```
