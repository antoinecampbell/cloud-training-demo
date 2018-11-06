INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$9ALpT96a4IAOBZ1BVnorKupnhCE6GM5VF3WHhKxAv402PX5NX4Gw2', TRUE),
  ('user', '$2a$10$t9Ebz8953gdqmB464K0RJ.vVe1.ZHu5P6l.k3W8oLDVx0me47qEg2', TRUE);

INSERT INTO authorities (username, authority)
VALUES   ('admin', 'ROLE_ADMIN'),
  ('user', 'ROLE_USER');
