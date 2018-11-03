INSERT INTO artist (id, name, first_name, last_name) VALUES (1, 'DJ Umek', 'Uroš', 'Umek'), (2, 'Valentino Kanzyani', 'Tine', 'Kocjančič'), (3, 'Paul Kalkbrenner', 'Paul', 'Kalkbrenner'), (4, 'Fritz Kalkbrenner', 'Fritz', 'Kalkbrenner'), (5, 'Miss K8', 'Kateryna', 'Kremko'), (6, 'Helena Hauff', 'Helena', 'Hauff');
INSERT INTO album (id, name, year) VALUES (1, 'Volume One on One', 2002), (2, 'Berlin Calling', 2008);
INSERT INTO artist_album (artist_id, album_id) VALUES (1, 1), (2, 1), (3, 2);
INSERT INTO song (id, name, duration, listens) VALUES (1, 'Sky and Sand', 239, 0), (2, 'Altes Kamuffel', 275, 0), (3, 'Too Many Answers', 172, 0);
INSERT INTO artist_song (artist_id, song_id) VALUES (3, 1), (3, 2), (4, 1), (5, 3);
INSERT INTO playlist (id, name) VALUES (1, 'Prijetne vize za lepsi vsakdan');
INSERT INTO playlist_song (playlist_id, song_id) VALUES (1, 2), (1, 3);

/* Need to reset the IDs manually (so that duplication exceptions do not happen) */
ALTER SEQUENCE artist_id_seq RESTART WITH 100;
ALTER SEQUENCE album_id_seq RESTART WITH 100;
ALTER SEQUENCE song_id_seq RESTART WITH 100;
ALTER SEQUENCE playlist_id_seq RESTART WITH 100;