DELETE FROM contacts;
DELETE FROM esme_jur_persons;
DELETE FROM connectors;
ALTER SEQUENCE connectors_id_seq RESTART WITH 1;
ALTER SEQUENCE contacts_id_seq RESTART WITH 1;

INSERT INTO connectors (name, system_id, port, enabled, smsc_addr, jur_person) VALUES
  ('MTS', 'systemId-MTS', 9090, true,'mts.ru','Vasilii'),
  ('Megafon', 'systemId-Megafon',  7895, false, 'megafon.ru','Genadiy');

INSERT INTO esme_jur_persons (connector_id,jur_person) VALUES
  (1449,'i-Free'),
  (1619,'BeFree');


INSERT INTO contacts (connector_id, contact, description,custom) VALUES
  ((SELECT id  FROM connectors WHERE name='MTS'),'phone_num','phone some manager',true),
  ((SELECT id  FROM esme_connectors WHERE name='A-mobile'),'email@a-mobile.ru', 'email tech support',false),
  ((SELECT id  FROM connectors WHERE name='Megafon'),'email@megafon.ru', 'email tech support',true),
  ((SELECT id  FROM esme_connectors WHERE name='AKOC'),'79254567891', 'phone some manager',false)