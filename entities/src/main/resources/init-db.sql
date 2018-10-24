INSERT INTO artist (id, name, first_name, last_name) VALUES (1, 'DJ Umek', 'Uroš', 'Umek'), (2, 'Valentino Kanzyani', null, null);
INSERT INTO album (id, name, year) VALUES (1, 'Volume One on One', 2002);
INSERT INTO artist_album (artist_id, album_id) VALUES (1, 1), (2, 1);
INSERT INTO song (name, duration, listens) VALUES ('', 120, 69);