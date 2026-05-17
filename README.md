# 🧠 Kafka NER System

A **real-time Named Entity Recognition (NER) pipeline** built on Apache Kafka. Text is produced through a REST API, streamed through Kafka topics, processed by a Python NER consumer, and analytics are served via a live dashboard.

---

## 🏗️ Architecture

```
┌─────────────────┐        ┌───────────────┐        ┌──────────────────┐
│  REST API Input │──────▶ │ Kafka Producer │──────▶ │   Kafka Topic    │
│  (Spring Boot)  │        │ (Spring Boot)  │        │   (ner-input)    │
└─────────────────┘        └───────────────┘        └────────┬─────────┘
                                                             │
                                          ┌──────────────────┼──────────────────┐
                                          ▼                                     ▼
                                 ┌─────────────────┐               ┌─────────────────────┐
                                 │  NER Consumer   │               │  Analytics Consumer  │
                                 │    (Python)     │               │   (Spring Boot)      │
                                 └────────┬────────┘               └──────────┬──────────┘
                                          │                                    │
                                          ▼                                    ▼
                                 ┌─────────────────┐               ┌─────────────────────┐
                                 │ Processed Entity │               │   Stats REST API     │
                                 │    Results       │               │   + Dashboard        │
                                 └─────────────────┘               └─────────────────────┘
```

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Message Broker | Apache Kafka |
| Producer | Spring Boot (Java) |
| NER Processing | Python (spaCy / HuggingFace) |
| Analytics | Spring Boot (Java) |
| Frontend | HTML Dashboard |
| Infrastructure | Docker & Docker Compose |

---

## ✨ Features

- ⚡ **Real-time text processing** via Kafka streaming
- 🏷️ **Named Entity Recognition** — detects Persons, Organizations, Locations, Dates, and more
- 📊 **Analytics dashboard** with live entity statistics
- 🐳 **Dockerized Kafka setup** for easy local development
- 🔌 **REST API** to submit text and retrieve entity stats

---

## 📁 Project Structure

```
kafka-ner-system/
│
├── producer/                        # Spring Boot Kafka Producer
│   ├── src/main/java/com/ner/producer/
│   │   ├── ProducerApplication.java
│   │   ├── TextController.java      # REST endpoint to accept text input
│   │   └── TextProducerService.java # Publishes text to Kafka topic
│   └── pom.xml
│
├── ner-consumer/                    # Python NER Consumer
│   └── ner_consumer.py              # Consumes from Kafka, runs NER, publishes results
│
├── analytics-consumer/              # Spring Boot Analytics Consumer
│   ├── src/main/java/com/ner/analytics/
│   │   ├── AnalyticsApplication.java
│   │   ├── AnalyticsConsumer.java   # Consumes NER results
│   │   ├── EntityRecord.java        # Entity data model
│   │   ├── EntityRecordRepository.java
│   │   └── StatsController.java    # REST endpoint for statistics
│   └── pom.xml
│
├── kafka-ner-system/
│   └── docker-compose.yml           # Kafka + Zookeeper setup
│
└── dashboard.html                   # Frontend analytics dashboard
```

---

## ⚙️ Prerequisites

- Java 17+
- Python 3.8+
- Docker & Docker Compose
- Maven

---

## 🛠️ How to Run

### 1. Start Kafka with Docker

```bash
cd kafka-ner-system
docker-compose up -d
```

### 2. Run the Producer (Spring Boot)

```bash
cd producer
./mvnw spring-boot:run
```

### 3. Run the NER Consumer (Python)

```bash
cd ner-consumer
pip install kafka-python spacy
python -m spacy download en_core_web_sm
python ner_consumer.py
```

### 4. Run the Analytics Consumer (Spring Boot)

```bash
cd analytics-consumer
./mvnw spring-boot:run
```

### 5. Open the Dashboard

Open `dashboard.html` in your browser.

---

## 📡 API Endpoints

### Producer
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/text` | Submit text for NER processing |

**Example:**
```bash
curl -X POST http://localhost:8080/api/text \
  -H "Content-Type: application/json" \
  -d '{"text": "Elon Musk founded SpaceX in Hawthorne, California."}'
```

### Analytics
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/stats` | Get entity recognition statistics |

---

## 📸 Dashboard Preview

> _Real-time entity statistics and recognition results displayed on the live dashboard._

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👩‍💻 Author

**Shradha Biradar**
- GitHub: [@ShradhaBiradar100](https://github.com/ShradhaBiradar100)
