-- Flyway script to insert product categories
INSERT INTO product_category (category_id, name, description, created_at, updated_at)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'Analgésicos', 'Medicamentos para el alivio del dolor', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440002', 'Antibióticos', 'Medicamentos para tratar infecciones bacterianas', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440003', 'Antiinflamatorios', 'Medicamentos que reducen la inflamación', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440004', 'Antihipertensivos', 'Medicamentos para tratar la presión arterial alta', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440005', 'Antidiabéticos', 'Medicamentos para el control de la diabetes', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440006', 'Antihistamínicos', 'Medicamentos para tratar alergias', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440007', 'Vitaminas y Suplementos', 'Complementos alimenticios y vitaminas', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440008', 'Dermatología', 'Productos para el cuidado y tratamiento de la piel', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440009', 'Oftalmología', 'Productos para el cuidado de los ojos', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440010', 'Gastroenterología', 'Medicamentos para el sistema digestivo', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440011', 'Cardiología', 'Medicamentos para la salud del corazón', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440012', 'Neurología', 'Medicamentos para el sistema nervioso', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440013', 'Oncología', 'Medicamentos para el tratamiento del cáncer', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440014', 'Material Médico', 'Insumos y equipos médicos', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440015', 'Cuidado Personal', 'Productos de higiene y cuidado diario', NOW(), NOW()),
    ('550e8400-e29b-41d4-a716-446655440016', 'Otro', 'Otras categorías no especificadas', NOW(), NOW());