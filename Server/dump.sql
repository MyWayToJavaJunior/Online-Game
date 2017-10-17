--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.8
-- Dumped by pg_dump version 9.5.8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.hero_states DROP CONSTRAINT hero_states_users_id_fk;
ALTER TABLE ONLY public.coordinates DROP CONSTRAINT coordinates_hero_states_id_fk;
DROP INDEX public.users_pass_token_uindex;
DROP INDEX public.users_login_uindex;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.hero_states DROP CONSTRAINT hero_states_pkey;
ALTER TABLE ONLY public.coordinates DROP CONSTRAINT coordinates_pkey;
ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.hero_states ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.coordinates ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.users_id_seq;
DROP TABLE public.users;
DROP SEQUENCE public.hero_states_id_seq;
DROP TABLE public.hero_states;
DROP SEQUENCE public.coordinates_id_seq;
DROP TABLE public.coordinates;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: coordinates; Type: TABLE; Schema: public; Owner: homestead
--

CREATE TABLE coordinates (
    id integer NOT NULL,
    x integer,
    y integer,
    state_id integer
);


ALTER TABLE coordinates OWNER TO homestead;

--
-- Name: coordinates_id_seq; Type: SEQUENCE; Schema: public; Owner: homestead
--

CREATE SEQUENCE coordinates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE coordinates_id_seq OWNER TO homestead;

--
-- Name: coordinates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: homestead
--

ALTER SEQUENCE coordinates_id_seq OWNED BY coordinates.id;


--
-- Name: hero_states; Type: TABLE; Schema: public; Owner: homestead
--

CREATE TABLE hero_states (
    id integer NOT NULL,
    health integer,
    user_id integer
);


ALTER TABLE hero_states OWNER TO homestead;

--
-- Name: hero_states_id_seq; Type: SEQUENCE; Schema: public; Owner: homestead
--

CREATE SEQUENCE hero_states_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hero_states_id_seq OWNER TO homestead;

--
-- Name: hero_states_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: homestead
--

ALTER SEQUENCE hero_states_id_seq OWNED BY hero_states.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: homestead
--

CREATE TABLE users (
    id integer NOT NULL,
    login character varying(255),
    password character varying(255),
    pass_token character varying(255),
    hero_token character varying(30)
);


ALTER TABLE users OWNER TO homestead;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: homestead
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO homestead;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: homestead
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY coordinates ALTER COLUMN id SET DEFAULT nextval('coordinates_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY hero_states ALTER COLUMN id SET DEFAULT nextval('hero_states_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: coordinates; Type: TABLE DATA; Schema: public; Owner: homestead
--

COPY coordinates (id, x, y, state_id) FROM stdin;
1	0	0	1
2	0	1	2
3	1	0	3
4	1	1	4
\.


--
-- Name: coordinates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: homestead
--

SELECT pg_catalog.setval('coordinates_id_seq', 4, true);


--
-- Data for Name: hero_states; Type: TABLE DATA; Schema: public; Owner: homestead
--

COPY hero_states (id, health, user_id) FROM stdin;
1	100	1
2	100	2
3	100	3
4	100	4
\.


--
-- Name: hero_states_id_seq; Type: SEQUENCE SET; Schema: public; Owner: homestead
--

SELECT pg_catalog.setval('hero_states_id_seq', 4, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: homestead
--

COPY users (id, login, password, pass_token, hero_token) FROM stdin;
1	user1	pass1	pass_token_1	X
2	user2	pass2	pass_token_2	O
3	user3	pass3	pass_token_3	#
4	user4	pass4	pass_token_4	$
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: homestead
--

SELECT pg_catalog.setval('users_id_seq', 4, true);


--
-- Name: coordinates_pkey; Type: CONSTRAINT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY coordinates
    ADD CONSTRAINT coordinates_pkey PRIMARY KEY (id);


--
-- Name: hero_states_pkey; Type: CONSTRAINT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY hero_states
    ADD CONSTRAINT hero_states_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_login_uindex; Type: INDEX; Schema: public; Owner: homestead
--

CREATE UNIQUE INDEX users_login_uindex ON users USING btree (login);


--
-- Name: users_pass_token_uindex; Type: INDEX; Schema: public; Owner: homestead
--

CREATE UNIQUE INDEX users_pass_token_uindex ON users USING btree (pass_token);


--
-- Name: coordinates_hero_states_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY coordinates
    ADD CONSTRAINT coordinates_hero_states_id_fk FOREIGN KEY (state_id) REFERENCES hero_states(id);


--
-- Name: hero_states_users_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: homestead
--

ALTER TABLE ONLY hero_states
    ADD CONSTRAINT hero_states_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

