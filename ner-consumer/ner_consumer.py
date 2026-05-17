from kafka import KafkaConsumer, KafkaProducer
import spacy, json

nlp = spacy.load("en_core_web_sm")

consumer = KafkaConsumer(
    'raw-text',
    bootstrap_servers=['localhost:9092'],
    group_id='ner-consumer-group',
    auto_offset_reset='earliest'
)

producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

print("NER Consumer started. Waiting for messages...")

for message in consumer:
    text = message.value.decode('utf-8')
    key = message.key.decode('utf-8') if message.key else "no-key"
    doc = nlp(text)
    entities = [{"text": ent.text, "label": ent.label_} for ent in doc.ents]
    result = {
        "message_id": key,
        "original_text": text,
        "entities": entities
    }
    producer.send('enriched-entities', value=result)
    print(f"Extracted {len(entities)} entities: {entities}")
