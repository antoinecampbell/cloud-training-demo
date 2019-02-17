INSERT INTO oauth_client_details (client_id, access_token_validity, additional_information, authorities,
                                  authorized_grant_types, autoapprove, client_secret, refresh_token_validity,
                                  resource_ids, scope, web_server_redirect_uri)
VALUES ('clientApp', 600, NULL, NULL, 'password,refresh_token', 'all',
        '$2a$10$VlhRkOge0qZTRA5zXzNENuknenjIE/.UsbFgXJdx8Zj0baVsP2O.u', 0, NULL, 'all', NULL);

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$9ALpT96a4IAOBZ1BVnorKupnhCE6GM5VF3WHhKxAv402PX5NX4Gw2', TRUE),
       ('user', '$2a$10$t9Ebz8953gdqmB464K0RJ.vVe1.ZHu5P6l.k3W8oLDVx0me47qEg2', TRUE);

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN'),
       ('admin', 'ROLE_USER'),
       ('user', 'ROLE_USER');
