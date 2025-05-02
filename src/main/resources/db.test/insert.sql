insert into championnat (nom, pays) values
                                        ('Premier League', 'Angleterre'),
                                        ('La Liga', 'Espagne'),
                                        ('Bundesliga', 'Allemagne'),
                                        ('Serie A', 'Italie'),
                                        ('Ligue 1', 'France');


insert into club (nom, acronyme, annee_creation, nom_stade, id_championnat) values
                                                                                ('Manchester United', 'MUFC', 1878, 'Old Trafford',
                                                                                 (select id_championnat from championnat where nom = 'Premier League')),
                                                                                ('Real Madrid', 'RMA', 1902, 'Santiago Bernabéu',
                                                                                 (select id_championnat from championnat where nom = 'La Liga')),
                                                                                ('Bayern Munich', 'FCB', 1900, 'Allianz Arena',
                                                                                 (select id_championnat from championnat where nom = 'Bundesliga')),
                                                                                ('Juventus', 'JUV', 1897, 'Allianz Stadium',
                                                                                 (select id_championnat from championnat where nom = 'Serie A')),
                                                                                ('Paris Saint-Germain', 'PSG', 1970, 'Parc des Princes',
                                                                                 (select id_championnat from championnat where nom = 'Ligue 1'));

insert into entraineur (nom, nationalite, id_club) values
                                                            ('Erik ten Hag', 'Pays-Bas',  (select id_club from club where nom = 'Manchester United')),
                                                            ('Carlo Ancelotti', 'Italie', (select id_club from club where nom = 'Real Madrid')),
                                                            ('Thomas Tuchel', 'Allemagne', (select id_club from club where nom = 'Bayern Munich')),
                                                            ('Massimiliano Allegri', 'Italie',  (select id_club from club where nom = 'Juventus')),
                                                            ('Luis Enrique', 'Espagne',  (select id_club from club where nom = 'Paris Saint-Germain'));


insert into joueur (nom, numero, poste, nationalite, age, id_club) values
                                                                       ('Marcus Rashford', 10, 'ATT', 'Angleterre', 27,
                                                                        (select id_club from club where nom = 'Manchester United')),
                                                                       ('Vinícius Júnior', 7, 'ATT', 'Brésil', 24,
                                                                        (select id_club from club where nom = 'Real Madrid')),
                                                                       ('Joshua Kimmich', 6, 'ML', 'Allemagne', 29,
                                                                        (select id_club from club where nom = 'Bayern Munich')),
                                                                       ('Dusan Vlahovic', 9, 'ATT', 'Serbie', 25,
                                                                        (select id_club from club where nom = 'Juventus')),
                                                                       ('Kylian Mbappé', 7, 'ATT', 'France', 26,
                                                                        (select id_club from club where nom = 'Paris Saint-Germain'));



            ------------------- next targert here::: ------------------

---- SAISON:
-- Insertion d'une saison
insert into saison (year, season_year, status)
values (2025, '2024-2025', 'STARTED');

-- Récupérer l'UUID de la saison
select id_saison from saison where season_year = '2024-2025';


----MATCH
-- Insertion d'un match entre PSG et Manchester United
insert into match (
    date_heure, stade, id_club_domicile, id_club_exterieur,
    score_domicile, score_exterieur, id_saison, id_championnat, status
) values (
             '2024-09-15 20:45:00', 'Parc des Princes',
             (select id_club from club where nom = 'Paris Saint-Germain'),
             (select id_club from club where nom = 'Manchester United'),
             2, 1,
             (select id_saison from saison where season_year = '2024-2025'),
             (select id_championnat from championnat where nom = 'Ligue 1'),
             'FINISHED'
         );

-- Récupérer l'UUID du match
select id_match from match where stade = 'Parc des Princes' and id_club_domicile = (select id_club from club where nom = 'Paris Saint-Germain');


---- STATS_CLUB_SAISON:
-- Insertion des stats pour PSG en saison 2024-2025
insert into stats_club_saison (
    id_club, id_saison, points, buts_marques, buts_encaisses, clean_sheets
) values (
             (select id_club from club where nom = 'Paris Saint-Germain'),
             (select id_saison from saison where season_year = '2024-2025'),
             3, 6, 2, 1
         );

-- Insertion des stats pour Manchester United en saison 2024-2025
insert into stats_club_saison (
    id_club, id_saison, points, buts_marques, buts_encaisses, clean_sheets
) values (
             (select id_club from club where nom = 'Manchester United'),
             (select id_saison from saison where season_year = '2024-2025'),
             0, 1, 3, 0
         );

-- Récupérer l'UUID des stats
select id_stats from stats_club_saison where id_club = (select id_club from club where nom = 'Paris Saint-Germain');


---- STATS_JOUEUR_SAISON:
-- Insertion des stats pour Mbappé en saison 2024-2025
insert into stats_joueur_saison (
    id_joueur, id_saison, buts_marques, buts_contre_son_camp, duree_jeu_value, duree_jeu_unit
) values (
             (select id_joueur from joueur where nom = 'Kylian Mbappé'),
             (select id_saison from saison where season_year = '2024-2025'),
             2, 0, 90, 'MINUTE'
         );

-- Insertion des stats pour Rashford en saison 2024-2025
insert into stats_joueur_saison (
    id_joueur, id_saison, buts_marques, buts_contre_son_camp, duree_jeu_value, duree_jeu_unit
) values (
             (select id_joueur from joueur where nom = 'Marcus Rashford'),
             (select id_saison from saison where season_year = '2024-2025'),
             1, 0, 90, 'MINUTE'
         );

-- Récupérer l'UUID des stats pour un joueur
select id_stats from stats_joueur_saison where id_joueur = (select id_joueur from joueur where nom = 'Kylian Mbappé');


---- BUT:
-- Insertion d'un but de Mbappé pour PSG contre Manchester United
insert into but (
    id_match, id_joueur, id_club, minute, contre_son_camp
) values (
             (select id_match from match where stade = 'Parc des Princes' and id_club_domicile = (select id_club from club where nom = 'Paris Saint-Germain')),
             (select id_joueur from joueur where nom = 'Kylian Mbappé'),
             (select id_club from club where nom = 'Paris Saint-Germain'),
             15, false
         );

-- Insertion d'un but de Rashford pour Manchester United contre PSG
insert into but (
    id_match, id_joueur, id_club, minute, contre_son_camp
) values (
             (select id_match from match where stade = 'Parc des Princes' and id_club_domicile = (select id_club from club where nom = 'Paris Saint-Germain')),
             (select id_joueur from joueur where nom = 'Marcus Rashford'),
             (select id_club from club where nom = 'Manchester United'),
             58, false
         );

-- Récupérer l'UUID d'un but
select id_but from but where id_match = (select id_match from match where stade = 'Parc des Princes');
