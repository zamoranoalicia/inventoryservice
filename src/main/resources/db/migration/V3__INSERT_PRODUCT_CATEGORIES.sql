-- Flyway script to insert product categories
INSERT INTO product_category (category_id, name, description, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'Analgésicos', 'Medicamentos para el alivio del dolor',now(),now()),
    (gen_random_uuid(), 'Antibióticos', 'Medicamentos para tratar infecciones bacterianas',now(),now()),
    (gen_random_uuid(), 'Antiinflamatorios', 'Medicamentos que reducen la inflamación',now(),now()),
    (gen_random_uuid(), 'Antihipertensivos', 'Medicamentos para tratar la presión arterial alta',now(),now()),
    (gen_random_uuid(), 'Antidiabéticos', 'Medicamentos para el control de la diabetes',now(),now()),
    (gen_random_uuid(), 'Antihistamínicos', 'Medicamentos para tratar alergias',now(),now()),
    (gen_random_uuid(), 'Vitaminas y Suplementos', 'Complementos alimenticios y vitaminas',now(),now()),
    (gen_random_uuid(), 'Dermatología', 'Productos para el cuidado y tratamiento de la piel',now(),now()),
    (gen_random_uuid(), 'Oftalmología', 'Productos para el cuidado de los ojos',now(),now()),
    (gen_random_uuid(), 'Gastroenterología', 'Medicamentos para el sistema digestivo',now(),now()),
    (gen_random_uuid(), 'Cardiología', 'Medicamentos para la salud del corazón',now(),now()),
    (gen_random_uuid(), 'Neurología', 'Medicamentos para el sistema nervioso',now(),now()),
    (gen_random_uuid(), 'Oncología', 'Medicamentos para el tratamiento del cáncer',now(),now()),
    (gen_random_uuid(), 'Material Médico', 'Insumos y equipos médicos',now(),now()),
    (gen_random_uuid(), 'Cuidado Personal', 'Productos de higiene y cuidado diario',now(),now()),
    (gen_random_uuid(), 'Otro', 'Otras categorías no especificadas',now(),now());