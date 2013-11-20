Simples chat usando socket e threads, feito em java.
==========

Exemplo de um chat usando socket em java.

Para iniciar o servidor, rode a classe main 
br/upis/chat/server/Server.java

Para iniciar o cliente, rode a classe 1x para o número de clientes desejados
br/upis/chat/client/Client.java

O arquivo Config.java, tem duas constantes de configuração de host e porta de conexão do socket.

A classe Server.java implementa IServerWriter.java, e inicializa um novo servidor de socket na porta descrita no arquivo Config.java;

A classe ServerClient.java é a thread responsável por tratar individualmente a conexão socket de cada cliente conectado, a classe recebe um inputstream do socket, que fica verificando a todo tempo se existe mensagem do cliente, para disparar um evento, chamada de metódo para a classe que implementa a interface IServerWriter.java.

A interface IServerWriter é apenas um listener para notificar o servidor,
que as mensagens que chegaram devem ser enviadas a todos o clientes conectados.



//Depois eu termino a descrição ;D
