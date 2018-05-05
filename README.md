# jsf-project-manager
Sistema de Análise e Seleção de Candidatos para Projetos Acadêmicos em um Banco de Dados Orientado a Grafos

Trabalho de conclusão de curso - Universidade do Vale do Sapucaí - UNIVÁS - Pouso Alegre/MG - 2017

# Autores
* [Fabiano Junior](https://github.com/lomonaco91)
  (fabiano.lomonaco.junior@gmail.com)
* [Luiz Eduardo Costa](https://github.com/oluizeduardo);
  (costaeduardoluiz@gmail.com)
  

# Ferramentas de Desenvolvimento
* JDK 8
* [Tomcat 7 ](https://tomcat.apache.org/)
* Gitbash
* Eclipse JEE
* [Primefaces](https://github.com/primefaces/primefaces)

# Banco de Dados
* [Neo4j](https://neo4j.com/)

# Como rodar? (Windows)
1. Faça o clone desse projeto para sua máquina. <br>
  `git clone https://github.com/oluizeduardo/jsf-project-manager.git`
2. Importe o projeto para a IDE Eclipse. <br>
  File > Import
3. Instale a versão 7 do [Tomcat](https://tomcat.apache.org/) no Eclipse.
4. Adicione o projeto `jsf-project-manager` no servidor Tomcat.
5. Faça o download do [Neo4j Server 3.3.5 versão Comunity](https://neo4j.com/download/other-releases/#releases). <br>
6. Descompacte o arquivo .zip e cole-o, de preferência em `C:/`.
7. Abra o `cmd` no Windows e execute o seguinte comando para instalação do servidor:<br> 
    `C:\bin\neo4j install-service`
8. Aguarde finalizar a instalação e em seguida execute o comando para iniciar o servidor:<br>
    `C:\bin\neo4j start`
9. Abra o browser e acesse:<br>
    `http://localhost:7474/browser/`
10. Conecte usando o username `neo4j` com a senha padrão `neo4j`.
11. Altere a senha do banco de dados para: `123456` (senha padrão definida no projeto).<br>
   > Caso queira definir uma outra senha para o banco de dados, você deve alterar a senha de acesso no projeto Java:
   `model.dao.databaseconfig.ConnectionNeo4j.java`
12. Execute o servidor Tomcat na IDE Eclipse.
13. Abra o browser e acesse:<br>
    `http://localhost:8080/ProjectManager`
    
# Caso ocorra falha ao carregar o tema do Primefaces:
  1. Interrompa o servidor Tomcat no Eclipse.
  2. Baixe o arquivo `south-street-1.0.10.jar` no site do [Maven](https://mvnrepository.com/artifact/org.primefaces.themes/south-street/1.0.10).
  3. Cole o arquivo baixado dentro do repositório local do Maven:<br>
  `C:\Users\Administrador\.m2\repository\org\primefaces\themes\south-street\1.0.10`
  4. Execute novamente o servidor Tomcat.
  5. Abra o browser e acesse:<br>
    `http://localhost:8080/ProjectManager`
