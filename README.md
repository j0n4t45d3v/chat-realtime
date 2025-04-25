# 💬 Chat Realtime via Sockets (Java CLI)

Este é um projeto de **chat em tempo real** desenvolvido em Java puro, utilizando
**Sockets** para comunicação e uma arquitetura baseada em **exchanges** e **channels**. 
O sistema permite a criação de **grupos**, **conversas privadas** e **salas de bate-papo**.
Tudo isso acessado via **linha de comando (CLI)**.

---

## 🚀 Funcionalidades

- 🔌 Conexão e desconexão via CLI
- 🧑‍🤝‍🧑 Suporte a **conversas privadas** e **em grupo**
- 🧱 Criação e gerenciamento de **canais de troca (channels)** e **exchanges**
- 🏷️ Associação dinâmica de clientes a canais
- 🛑 Encerramento de conexão com o servidor via comandos
- ⚙️ Arquitetura flexível para expansão futura

---

## 🧰 Tecnologias utilizadas

- Java 21+
- Java Sockets (`java.net.ServerSocket`, `Socket`)
- Threads para controle simultâneo de clientes
- Coleções sincronizadas para gerenciamento de estado
- CLI para interação em tempo real
- **Java 21+** - linguagem
- **Java Sockets** (`java.net.ServerSocket`, `Socket`)
- **Threads** - controle simultâneo de clientes
- **ConcurrentMap** - Coleções sincronizadas

---

## 🧠 Como funciona

### 🧩 Arquitetura Exchange/Channel

- **Exchange**: Gerencia a distribuição de mensagens entre diferentes canais
- **Channel**: Representa uma sala de conversa (pública ou privada)
- **ClientHandler**: Cada cliente conectado roda em uma thread separada
- **CLI**: Interface textual para entrada de comandos do usuário

---

## 💻 Execução

### ✅ Requisitos

- Java 21+ instalado

### ▶️ Rodar o servidor

```bash
    javac Server.java
    java Server
```
### 💬 Rodar um cliente
```bash
    javac Client.java
    java Client
```
