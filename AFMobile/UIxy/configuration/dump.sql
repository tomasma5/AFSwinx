--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

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
-- Name: application; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE application (
    application_id integer NOT NULL,
    application_name character varying(255),
    consumer_context_path character varying(255),
    consumer_hostname character varying(255),
    consumer_port integer,
    consumer_protocol character varying(255),
    proxy_hostname character varying(255),
    proxy_port integer,
    proxy_protocol character varying(255),
    remote_hostname character varying(255),
    remote_port integer,
    remote_protocol character varying(255),
    application_uuid character varying(255)
);


ALTER TABLE application OWNER TO uixy_user;

--
-- Name: application_application_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE application_application_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE application_application_id_seq OWNER TO uixy_user;

--
-- Name: application_application_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE application_application_id_seq OWNED BY application.application_id;


--
-- Name: bcfield; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE bcfield (
    bc_field_id integer NOT NULL,
    component_id integer,
    field_id integer,
    fieldspecification_field_severity_id integer,
    phase_id integer,
    screen_id integer
);


ALTER TABLE bcfield OWNER TO uixy_user;

--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE bcfield_bc_field_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bcfield_bc_field_id_seq OWNER TO uixy_user;

--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE bcfield_bc_field_id_seq OWNED BY bcfield.bc_field_id;


--
-- Name: bcfieldseverity; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE bcfieldseverity (
    field_severity_id integer NOT NULL,
    purpose character varying(255),
    score double precision,
    severity character varying(255),
    field_bc_field_id integer
);


ALTER TABLE bcfieldseverity OWNER TO uixy_user;

--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE bcfieldseverity_field_severity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bcfieldseverity_field_severity_id_seq OWNER TO uixy_user;

--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE bcfieldseverity_field_severity_id_seq OWNED BY bcfieldseverity.field_severity_id;


--
-- Name: business_case; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE business_case (
    business_case_id integer NOT NULL,
    description character varying(255),
    name character varying(255),
    application_id integer
);


ALTER TABLE business_case OWNER TO uixy_user;

--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE business_case_business_case_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE business_case_business_case_id_seq OWNER TO uixy_user;

--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE business_case_business_case_id_seq OWNED BY business_case.business_case_id;


--
-- Name: business_phase; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE business_phase (
    phase_id integer NOT NULL,
    classification_unit character varying(255),
    phase_name character varying(255),
    scoringunit character varying(255),
    business_case_id integer,
    configuration_configuration_pack_id integer
);


ALTER TABLE business_phase OWNER TO uixy_user;

--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE business_phase_phase_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE business_phase_phase_id_seq OWNER TO uixy_user;

--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE business_phase_phase_id_seq OWNED BY business_phase.phase_id;


--
-- Name: component; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE component (
    component_id integer NOT NULL,
    field_info_hostname character varying(255),
    field_info_parameters character varying(255),
    field_info_port integer,
    field_info_protocol character varying(255),
    name character varying(255),
    type character varying(255),
    application_id integer,
    proxyconnections_connection_pack_id integer
);


ALTER TABLE component OWNER TO uixy_user;

--
-- Name: component_component_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE component_component_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE component_component_id_seq OWNER TO uixy_user;

--
-- Name: component_component_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE component_component_id_seq OWNED BY component.component_id;


--
-- Name: component_connection; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE component_connection (
    connection_id integer NOT NULL,
    address character varying(255),
    parameters character varying(255),
    port integer,
    protocol character varying(255),
    real_address character varying(255),
    real_parameters character varying(255),
    real_port integer,
    real_protocol character varying(255)
);


ALTER TABLE component_connection OWNER TO uixy_user;

--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE component_connection_connection_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE component_connection_connection_id_seq OWNER TO uixy_user;

--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE component_connection_connection_id_seq OWNED BY component_connection.connection_id;


--
-- Name: component_connection_pack; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE component_connection_pack (
    connection_pack_id integer NOT NULL,
    dataconnection_connection_id integer,
    modelconnection_connection_id integer,
    sendconnection_connection_id integer
);


ALTER TABLE component_connection_pack OWNER TO uixy_user;

--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE component_connection_pack_connection_pack_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE component_connection_pack_connection_pack_id_seq OWNER TO uixy_user;

--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE component_connection_pack_connection_pack_id_seq OWNED BY component_connection_pack.connection_pack_id;


--
-- Name: component_screen; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE component_screen (
    component_id integer NOT NULL,
    screen_id integer NOT NULL
);


ALTER TABLE component_screen OWNER TO uixy_user;

--
-- Name: configuration; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE configuration (
    configuration_id integer NOT NULL,
    behavior character varying(255),
    threshold_end double precision,
    threshold_start double precision,
    configurationpack_configuration_pack_id integer
);


ALTER TABLE configuration OWNER TO uixy_user;

--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE configuration_configuration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE configuration_configuration_id_seq OWNER TO uixy_user;

--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE configuration_configuration_id_seq OWNED BY configuration.configuration_id;


--
-- Name: configuration_pack; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE configuration_pack (
    configuration_pack_id integer NOT NULL,
    pack_name character varying(255),
    application_id integer
);


ALTER TABLE configuration_pack OWNER TO uixy_user;

--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE configuration_pack_configuration_pack_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE configuration_pack_configuration_pack_id_seq OWNER TO uixy_user;

--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE configuration_pack_configuration_pack_id_seq OWNED BY configuration_pack.configuration_pack_id;


--
-- Name: connection_headerparams; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE connection_headerparams (
    connection_id integer NOT NULL,
    param_value character varying(255),
    param_name character varying(255) NOT NULL
);


ALTER TABLE connection_headerparams OWNER TO uixy_user;

--
-- Name: connection_securityparams; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE connection_securityparams (
    connection_id integer NOT NULL,
    param_value character varying(255),
    param_name character varying(255) NOT NULL
);


ALTER TABLE connection_securityparams OWNER TO uixy_user;

--
-- Name: field; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE field (
    field_id integer NOT NULL,
    class_name character varying(255),
    field_name character varying(255),
    field_type character varying(255),
    value character varying(255)
);


ALTER TABLE field OWNER TO uixy_user;

--
-- Name: field_field_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE field_field_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE field_field_id_seq OWNER TO uixy_user;

--
-- Name: field_field_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE field_field_id_seq OWNED BY field.field_id;


--
-- Name: screen; Type: TABLE; Schema: public; Owner: uixy_user
--

CREATE TABLE screen (
    screen_id integer NOT NULL,
    key character varying(255),
    menu_order integer,
    name character varying(255),
    url character varying(255),
    application_id integer,
    phase_id integer
);


ALTER TABLE screen OWNER TO uixy_user;

--
-- Name: screen_screen_id_seq; Type: SEQUENCE; Schema: public; Owner: uixy_user
--

CREATE SEQUENCE screen_screen_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE screen_screen_id_seq OWNER TO uixy_user;

--
-- Name: screen_screen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: uixy_user
--

ALTER SEQUENCE screen_screen_id_seq OWNED BY screen.screen_id;


--
-- Name: application application_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY application ALTER COLUMN application_id SET DEFAULT nextval('application_application_id_seq'::regclass);


--
-- Name: bcfield bc_field_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield ALTER COLUMN bc_field_id SET DEFAULT nextval('bcfield_bc_field_id_seq'::regclass);


--
-- Name: bcfieldseverity field_severity_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfieldseverity ALTER COLUMN field_severity_id SET DEFAULT nextval('bcfieldseverity_field_severity_id_seq'::regclass);


--
-- Name: business_case business_case_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_case ALTER COLUMN business_case_id SET DEFAULT nextval('business_case_business_case_id_seq'::regclass);


--
-- Name: business_phase phase_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_phase ALTER COLUMN phase_id SET DEFAULT nextval('business_phase_phase_id_seq'::regclass);


--
-- Name: component component_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component ALTER COLUMN component_id SET DEFAULT nextval('component_component_id_seq'::regclass);


--
-- Name: component_connection connection_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection ALTER COLUMN connection_id SET DEFAULT nextval('component_connection_connection_id_seq'::regclass);


--
-- Name: component_connection_pack connection_pack_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection_pack ALTER COLUMN connection_pack_id SET DEFAULT nextval('component_connection_pack_connection_pack_id_seq'::regclass);


--
-- Name: configuration configuration_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration ALTER COLUMN configuration_id SET DEFAULT nextval('configuration_configuration_id_seq'::regclass);


--
-- Name: configuration_pack configuration_pack_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration_pack ALTER COLUMN configuration_pack_id SET DEFAULT nextval('configuration_pack_configuration_pack_id_seq'::regclass);


--
-- Name: field field_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY field ALTER COLUMN field_id SET DEFAULT nextval('field_field_id_seq'::regclass);


--
-- Name: screen screen_id; Type: DEFAULT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY screen ALTER COLUMN screen_id SET DEFAULT nextval('screen_screen_id_seq'::regclass);


--
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY application (application_id, application_name, consumer_context_path, consumer_hostname, consumer_port, consumer_protocol, proxy_hostname, proxy_port, proxy_protocol, remote_hostname, remote_port, remote_protocol, application_uuid) FROM stdin;
1	Showcase	/NSRest	localhost	8082	http	192.168.0.94	8081	http	localhost	8080	http	b1892f65-e620-4bed-a59e-cadc80aa9f3b
\.


--
-- Data for Name: bcfield; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfield (bc_field_id, component_id, field_id, fieldspecification_field_severity_id, phase_id, screen_id) FROM stdin;
12	4	10	11	3	1
13	4	11	12	3	1
14	9	12	13	4	7
15	9	13	14	4	7
16	9	14	15	4	7
17	9	15	16	4	7
18	9	16	17	4	7
19	9	17	18	4	7
20	9	18	19	4	7
21	9	19	20	4	7
22	9	20	21	4	7
23	9	21	22	4	7
24	9	22	23	4	7
25	9	23	24	4	7
26	9	24	25	4	7
27	9	25	26	4	7
\.


--
-- Data for Name: bcfieldseverity; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfieldseverity (field_severity_id, purpose, score, severity, field_bc_field_id) FROM stdin;
11	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
12	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
13	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
14	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
15	SYSTEM_INFORMATION	\N	REQUIRED	\N
16	SYSTEM_INFORMATION	\N	REQUIRED	\N
17	INFORMATION_MINING	\N	NEEDED	\N
18	SYSTEM_INFORMATION	\N	REQUIRED	\N
19	SYSTEM_INFORMATION	\N	REQUIRED	\N
20	SYSTEM_INFORMATION	\N	REQUIRED	\N
21	SYSTEM_INFORMATION	\N	REQUIRED	\N
22	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
23	FUTURE_INTERACTION	\N	CRITICAL	\N
24	SYSTEM_IDENTIFICATION	\N	CRITICAL	\N
25	INFORMATION_MINING	\N	NICE_TO_HAVE	\N
26	INFORMATION_MINING	\N	NEEDED	\N
\.


--
-- Data for Name: business_case; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_case (business_case_id, description, name, application_id) FROM stdin;
1	test	Business casee	1
5	Edits user properties	Profile edition	1
\.


--
-- Data for Name: business_phase; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_phase (phase_id, classification_unit, phase_name, scoringunit, business_case_id, configuration_configuration_pack_id) FROM stdin;
3	BASIC	rewr	BASIC	1	1
4	BASIC	Profile edit	NEARBY_DEVICE_SCORING	5	1
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component (component_id, field_info_hostname, field_info_parameters, field_info_port, field_info_protocol, name, type, application_id, proxyconnections_connection_pack_id) FROM stdin;
4	localhost	/AFServer/rest/login/fieldInfo	8080	http	loginForm	FORM	1	4
5	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryForm	FORM	1	5
6	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryTable	TABLE	1	6
7	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesTable	TABLE	1	7
8	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesForm	FORM	1	8
9	localhost	/AFServer/rest/users/fieldInfo	8080	http	personProfileForm	FORM	1	9
10	localhost	/AFServer/rest/absencetype/supportedCountries/fieldInfo	8080	http	absenceCountryForm	FORM	1	10
11	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeTable	TABLE	1	11
12	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeForm	FORM	1	12
13	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceForm	FORM	1	13
14	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceTable	TABLE	1	14
15	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditTable	TABLE	1	15
16	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditForm	FORM	1	16
17	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripTable	TABLE	1	17
18	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripForm	FORM	1	18
19	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartTable	TABLE	1	19
20	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartForm	FORM	1	20
21	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryList	LIST	1	21
22	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesList	LIST	1	22
23	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeList	LIST	1	23
24	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceList	LIST	1	24
25	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditList	LIST	1	25
26	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripList	LIST	1	26
27	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartList	LIST	1	27
\.


--
-- Data for Name: component_connection; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection (connection_id, address, parameters, port, protocol, real_address, real_parameters, real_port, real_protocol) FROM stdin;
41	192.168.0.94	/UIxy/api/connections/model/component/24	8081	http	localhost	/AFServer/rest/absenceInstance/definition/#{username}	8080	http
43	192.168.0.94	/UIxy/api/connections/model/component/25	8081	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
44	192.168.0.94	/UIxy/api/connections/data/component/25	8081	http	localhost	/AFServer/rest/absenceInstance/editable/#{username}	8080	http
42	192.168.0.94	/UIxy/api/connections/data/component/24	8081	http	localhost	/AFServer/rest/absenceInstance/user/#{username}	8080	http
39	192.168.0.94	/UIxy/api/connections/model/component/23	8081	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
40	192.168.0.94	/UIxy/api/connections/data/component/23	8081	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
14	192.168.0.94	/UIxy/api/connections/model/component/10	8081	http	localhost	/AFServer/rest/absencetype/definition/supportedCountries	8080	http
11	192.168.0.94	/UIxy/api/connections/model/component/9	8081	http	localhost	/AFServer/rest/users/profile	8080	http
12	192.168.0.94	/UIxy/api/connections/data/component/9	8081	http	localhost	/AFServer/rest/users/user/#{username}	8080	http
13	192.168.0.94	/UIxy/api/connections/send/component/9	8081	http	localhost	/AFServer/rest/users/update	8080	http
45	192.168.0.94	/UIxy/api/connections/model/component/26	8081	http	localhost	/AFServer/rest/businessTrip/definition/	8080	http
46	192.168.0.94	/UIxy/api/connections/data/component/26	8081	http	localhost	/AFServer/rest/businessTrip/user/#{username}	8080	http
23	192.168.0.94	/UIxy/api/connections/model/component/15	8081	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
24	192.168.0.94	/UIxy/api/connections/data/component/15	8081	http	localhost	/AFServer/rest/absenceInstance/editable/#{username}	8080	http
35	192.168.0.94	/UIxy/api/connections/model/component/21	8081	http	localhost	/AFServer/rest/country/definition	8080	http
36	192.168.0.94	/UIxy/api/connections/data/component/21	8081	http	localhost	/AFServer/rest/country/list	8080	http
3	192.168.0.94	/UIxy/api/connections/model/component/5	8081	http	localhost	/AFServer/rest/country/definition	8080	http
4	192.168.0.94	/UIxy/api/connections/send/component/5	8081	http	localhost	/AFServer/rest/country	8080	http
9	192.168.0.94	/UIxy/api/connections/model/component/8	8081	http	localhost	/AFServer/rest/vehicle/definition	8080	http
10	192.168.0.94	/UIxy/api/connections/send/component/8	8081	http	localhost	/AFServer/rest/vehicle	8080	http
21	192.168.0.94	/UIxy/api/connections/model/component/14	8081	http	localhost	/AFServer/rest/absenceInstance/definition/#{username}	8080	http
22	192.168.0.94	/UIxy/api/connections/data/component/14	8081	http	localhost	/AFServer/rest/absenceInstance/user/#{username}	8080	http
19	192.168.0.94	/UIxy/api/connections/model/component/13	8081	http	localhost	/AFServer/rest/absenceInstance/definitionAdd/#{user}	8080	http
20	192.168.0.94	/UIxy/api/connections/send/component/13	8081	http	localhost	/AFServer/rest/absenceInstance/add/#{user}	8080	http
25	192.168.0.94	/UIxy/api/connections/model/component/16	8081	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
26	192.168.0.94	/UIxy/api/connections/send/component/16	8081	http	localhost	/AFServer/rest/absenceInstance/add/#{username}	8080	http
7	192.168.0.94	/UIxy/api/connections/model/component/7	8081	http	localhost	/AFServer/rest/vehicle/definition	8080	http
8	192.168.0.94	/UIxy/api/connections/data/component/7	8081	http	localhost	/AFServer/rest/vehicle/list	8080	http
1	192.168.0.94	/UIxy/api/connections/model/component/4	8081	http	localhost	/AFServer/rest/login/definition	8080	http
2	192.168.0.94	/UIxy/api/connections/send/component/4	8081	http	localhost	/AFServer/rest/login/	8080	http
31	192.168.0.94	/UIxy/api/connections/model/component/19	8081	http	localhost	/AFServer/rest/businessTripPart/definition/	8080	http
32	192.168.0.94	/UIxy/api/connections/data/component/19	8081	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/list/#{businessTripId}	8080	http
47	192.168.0.94	/UIxy/api/connections/model/component/27	8081	http	localhost	/AFServer/rest/businessTripPart/definition/	8080	http
48	192.168.0.94	/UIxy/api/connections/data/component/27	8081	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/list/#{businessTripId}	8080	http
29	192.168.0.94	/UIxy/api/connections/model/component/18	8081	http	localhost	/AFServer/rest/businessTrip/definitionAdd/	8080	http
30	192.168.0.94	/UIxy/api/connections/send/component/18	8081	http	localhost	/AFServer/rest/businessTrip/add/#{username}	8080	http
37	192.168.0.94	/UIxy/api/connections/model/component/22	8081	http	localhost	/AFServer/rest/vehicle/definition	8080	http
38	192.168.0.94	/UIxy/api/connections/data/component/22	8081	http	localhost	/AFServer/rest/vehicle/list	8080	http
27	192.168.0.94	/UIxy/api/connections/model/component/17	8081	http	localhost	/AFServer/rest/businessTrip/definition/	8080	http
28	192.168.0.94	/UIxy/api/connections/data/component/17	8081	http	localhost	/AFServer/rest/businessTrip/user/#{username}	8080	http
33	192.168.0.94	/UIxy/api/connections/model/component/20	8081	http	localhost	/AFServer/rest/businessTripPart/definitionAdd/	8080	http
34	192.168.0.94	/UIxy/api/connections/send/component/20	8081	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/add/#{businessTripId}	8080	http
17	192.168.0.94	/UIxy/api/connections/model/component/12	8081	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
18	192.168.0.94	/UIxy/api/connections/send/component/12	8081	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
5	192.168.0.94	/UIxy/api/connections/model/component/6	8081	http	localhost	/AFServer/rest/country/definition	8080	http
6	192.168.0.94	/UIxy/api/connections/data/component/6	8081	http	localhost	/AFServer/rest/country/list	8080	http
15	192.168.0.94	/UIxy/api/connections/model/component/11	8081	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
16	192.168.0.94	/UIxy/api/connections/data/component/11	8081	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
\.


--
-- Data for Name: component_connection_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection_pack (connection_pack_id, dataconnection_connection_id, modelconnection_connection_id, sendconnection_connection_id) FROM stdin;
1	\N	\N	\N
2	\N	\N	\N
3	\N	\N	\N
4	\N	1	2
5	\N	3	4
6	6	5	\N
7	8	7	\N
8	\N	9	10
9	12	11	13
10	\N	14	\N
11	16	15	\N
12	\N	17	18
13	\N	19	20
14	22	21	\N
15	24	23	\N
16	\N	25	26
17	28	27	\N
18	\N	29	30
19	32	31	\N
20	\N	33	34
21	36	35	\N
22	38	37	\N
23	40	39	\N
24	42	41	\N
25	44	43	\N
26	46	45	\N
27	48	47	\N
\.


--
-- Data for Name: component_screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_screen (component_id, screen_id) FROM stdin;
1	4
7	9
8	23
8	11
8	12
8	10
6	7
6	8
6	22
5	5
5	6
5	21
9	13
10	14
10	24
12	20
12	26
12	19
12	17
12	18
12	27
11	16
11	15
11	25
\.


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration (configuration_id, behavior, threshold_end, threshold_start, configurationpack_configuration_pack_id) FROM stdin;
1	NOT_PRESENT	10	0	1
2	HIDDEN	40	10	1
3	ONLY_DISPLAY	60	40	1
4	VALIDATION	90	60	1
5	REQUIRED	100	90	1
\.


--
-- Data for Name: configuration_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration_pack (configuration_pack_id, pack_name, application_id) FROM stdin;
1	config2	1
\.


--
-- Data for Name: connection_headerparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_headerparams (connection_id, param_value, param_name) FROM stdin;
1	application/json	content-type
2	application/json	content-type
3	application/json	content-type
5	application/json	content-type
6	application/json	content-type
7	application/json	content-type
8	application/json	content-type
9	application/json	content-type
10	application/json	content-type
11	application/json	content-type
14	application/json	content-type
15	application/json	content-type
17	application/json	content-type
19	application/json	content-type
21	application/json	content-type
23	application/json	content-type
25	application/json	content-type
27	application/json	content-type
29	application/json	content-type
31	application/json	content-type
33	application/json	content-type
35	application/json	content-type
36	application/json	content-type
37	application/json	content-type
38	application/json	content-type
39	application/json	content-type
41	application/json	content-type
43	application/json	content-type
45	application/json	content-type
47	application/json	content-type
30	application/json	content-type
\.


--
-- Data for Name: connection_securityparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_securityparams (connection_id, param_value, param_name) FROM stdin;
4	#{password}	password
4	basic	securityMethod
4	#{username}	username
5	#{password}	password
5	basic	securityMethod
5	#{username}	username
7	#{password}	password
7	basic	securityMethod
7	#{username}	username
9	#{password}	password
9	basic	securityMethod
9	#{username}	username
10	#{password}	password
10	basic	securityMethod
10	#{username}	username
12	#{password}	password
12	basic	securityMethod
12	#{username}	username
13	#{password}	password
13	basic	securityMethod
13	#{username}	username
18	#{password}	password
18	basic	securityMethod
18	#{username}	username
19	#{password}	password
19	basic	securityMethod
19	#{username}	username
20	#{password}	password
20	basic	securityMethod
20	#{username}	username
21	#{password}	password
21	basic	securityMethod
21	#{username}	username
22	#{password}	password
22	basic	securityMethod
22	#{username}	username
23	#{password}	password
23	basic	securityMethod
23	#{username}	username
24	#{password}	password
24	basic	securityMethod
24	#{username}	username
25	#{username}	password
25	basic	securityMethod
25	#{username}	username
26	#{password}	password
26	basic	securityMethod
26	#{username}	username
28	#{password}	password
28	basic	securityMethod
28	#{username}	username
29	#{password}	password
29	basic	securityMethod
29	#{username}	username
30	#{password}	password
30	basic	securityMethod
30	#{username}	username
32	#{password}	password
32	basic	securityMethod
32	#{username}	username
33	#{password}	password
33	basic	securityMethod
33	#{username}	username
34	#{password}	password
34	basic	securityMethod
34	#{username}	username
35	#{password}	password
35	basic	securityMethod
35	#{username}	username
37	#{password}	password
37	basic	securityMethod
37	#{username}	username
41	#{password}	password
41	basic	securityMethod
41	#{username}	username
42	#{password}	password
42	basic	securityMethod
42	#{username}	username
43	#{password}	password
43	basic	securityMethod
43	#{username}	username
44	#{password}	password
44	basic	securityMethod
44	#{username}	username
46	#{password}	password
46	basic	securityMethod
46	#{username}	username
48	#{password}	password
48	basic	securityMethod
48	#{username}	username
\.


--
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY field (field_id, class_name, field_name, field_type, value) FROM stdin;
10	loginFormDefinitions	username	TEXTFIELD	\N
11	loginFormDefinitions	password	PASSWORD	\N
12	person	login	TEXTFIELD	\N
13	person	password	PASSWORD	\N
14	person	firstName	TEXTFIELD	\N
15	person	lastName	TEXTFIELD	\N
16	person	age	NUMBERFIELD	\N
17	person	myAddress.street	TEXTFIELD	\N
18	person	myAddress.city	TEXTFIELD	\N
19	person	myAddress.postCode	NUMBERFIELD	\N
20	person	myAddress.country	DROPDOWNMENU	\N
21	person	active	OPTION	\N
22	person	confidentialAgreement	CHECKBOX	\N
23	person	email	TEXTFIELD	\N
24	person	gender	OPTION	\N
25	person	hireDate	CALENDAR	\N
\.


--
-- Data for Name: screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY screen (screen_id, key, menu_order, name, url, application_id, phase_id) FROM stdin;
7	Profile	4	My profile	http://192.168.0.94:8081/UIxy/api/screens/7	1	4
1	Login	1	Login	http://192.168.0.94:8081/UIxy/api/screens/1	1	3
5	Supported countries	2	Supported countries	http://192.168.0.94:8081/UIxy/api/screens/5	1	\N
6	Vehicles	3	Vehicle management	http://192.168.0.94:8081/UIxy/api/screens/6	1	\N
8	Absence type management	5	Absence type management	http://192.168.0.94:8081/UIxy/api/screens/8	1	\N
9	Create absence	6	Create Absence	http://192.168.0.94:8081/UIxy/api/screens/9	1	\N
10	My absences	7	My absences	http://192.168.0.94:8081/UIxy/api/screens/10	1	\N
11	Absence management	8	\N	http://192.168.0.94:8081/UIxy/api/screens/11	1	\N
12	Business Trips	9	\N	http://192.168.0.94:8081/UIxy/api/screens/12	1	\N
\.


--
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('application_application_id_seq', 1, true);


--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfield_bc_field_id_seq', 27, true);


--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfieldseverity_field_severity_id_seq', 26, true);


--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_case_business_case_id_seq', 5, true);


--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_phase_phase_id_seq', 4, true);


--
-- Name: component_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_component_id_seq', 27, true);


--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_connection_id_seq', 48, true);


--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_pack_connection_pack_id_seq', 27, true);


--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_configuration_id_seq', 5, true);


--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_pack_configuration_pack_id_seq', 1, true);


--
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('field_field_id_seq', 25, true);


--
-- Name: screen_screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('screen_screen_id_seq', 12, true);


--
-- Name: application application_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (application_id);


--
-- Name: bcfield bcfield_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT bcfield_pkey PRIMARY KEY (bc_field_id);


--
-- Name: bcfieldseverity bcfieldseverity_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfieldseverity
    ADD CONSTRAINT bcfieldseverity_pkey PRIMARY KEY (field_severity_id);


--
-- Name: business_case business_case_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_case
    ADD CONSTRAINT business_case_pkey PRIMARY KEY (business_case_id);


--
-- Name: business_phase business_phase_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_phase
    ADD CONSTRAINT business_phase_pkey PRIMARY KEY (phase_id);


--
-- Name: component_connection_pack component_connection_pack_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection_pack
    ADD CONSTRAINT component_connection_pack_pkey PRIMARY KEY (connection_pack_id);


--
-- Name: component_connection component_connection_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection
    ADD CONSTRAINT component_connection_pkey PRIMARY KEY (connection_id);


--
-- Name: component component_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component
    ADD CONSTRAINT component_pkey PRIMARY KEY (component_id);


--
-- Name: configuration_pack configuration_pack_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration_pack
    ADD CONSTRAINT configuration_pack_pkey PRIMARY KEY (configuration_pack_id);


--
-- Name: configuration configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration
    ADD CONSTRAINT configuration_pkey PRIMARY KEY (configuration_id);


--
-- Name: connection_headerparams connection_headerparams_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY connection_headerparams
    ADD CONSTRAINT connection_headerparams_pkey PRIMARY KEY (connection_id, param_name);


--
-- Name: connection_securityparams connection_securityparams_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY connection_securityparams
    ADD CONSTRAINT connection_securityparams_pkey PRIMARY KEY (connection_id, param_name);


--
-- Name: field field_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY field
    ADD CONSTRAINT field_pkey PRIMARY KEY (field_id);


--
-- Name: screen screen_pkey; Type: CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY screen
    ADD CONSTRAINT screen_pkey PRIMARY KEY (screen_id);


--
-- Name: component_connection_pack fk1buch10qfefmg59tci4okppvd; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection_pack
    ADD CONSTRAINT fk1buch10qfefmg59tci4okppvd FOREIGN KEY (dataconnection_connection_id) REFERENCES component_connection(connection_id);


--
-- Name: component_screen fk1cr3ybkng1oseofoyk0p9rclc; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_screen
    ADD CONSTRAINT fk1cr3ybkng1oseofoyk0p9rclc FOREIGN KEY (component_id) REFERENCES screen(screen_id);


--
-- Name: bcfield fk2lmnbij43gxrfg3xlhm4khmeo; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT fk2lmnbij43gxrfg3xlhm4khmeo FOREIGN KEY (component_id) REFERENCES component(component_id);


--
-- Name: screen fk35ud4h0c0l0art18clnhdynsw; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY screen
    ADD CONSTRAINT fk35ud4h0c0l0art18clnhdynsw FOREIGN KEY (phase_id) REFERENCES business_phase(phase_id);


--
-- Name: business_phase fk6ov287ecld3tg1tm06u53wvna; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_phase
    ADD CONSTRAINT fk6ov287ecld3tg1tm06u53wvna FOREIGN KEY (business_case_id) REFERENCES business_case(business_case_id);


--
-- Name: bcfield fkd0q35rdfldy2arp67hwsnr3s5; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT fkd0q35rdfldy2arp67hwsnr3s5 FOREIGN KEY (field_id) REFERENCES field(field_id);


--
-- Name: component fkd7t2cgdhbq0wm8ktrvk669fnr; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fkd7t2cgdhbq0wm8ktrvk669fnr FOREIGN KEY (application_id) REFERENCES application(application_id);


--
-- Name: business_case fkdru8au130bro3jr3hcqjwa8t4; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_case
    ADD CONSTRAINT fkdru8au130bro3jr3hcqjwa8t4 FOREIGN KEY (application_id) REFERENCES application(application_id);


--
-- Name: business_phase fke476x3avjqxnxhu1ijeuejq7r; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY business_phase
    ADD CONSTRAINT fke476x3avjqxnxhu1ijeuejq7r FOREIGN KEY (configuration_configuration_pack_id) REFERENCES configuration_pack(configuration_pack_id);


--
-- Name: bcfield fkegn4rvggp8g2jwb77ekatfirf; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT fkegn4rvggp8g2jwb77ekatfirf FOREIGN KEY (phase_id) REFERENCES business_phase(phase_id);


--
-- Name: component_connection_pack fkek28ik8ax5kb8pd4y1hbni4qv; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection_pack
    ADD CONSTRAINT fkek28ik8ax5kb8pd4y1hbni4qv FOREIGN KEY (sendconnection_connection_id) REFERENCES component_connection(connection_id);


--
-- Name: component fkekniqiimrc8yqb1c5p535jgs0; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fkekniqiimrc8yqb1c5p535jgs0 FOREIGN KEY (proxyconnections_connection_pack_id) REFERENCES component_connection_pack(connection_pack_id);


--
-- Name: component_screen fkeujaonkb0jlnkylxiaiiyy3c1; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_screen
    ADD CONSTRAINT fkeujaonkb0jlnkylxiaiiyy3c1 FOREIGN KEY (screen_id) REFERENCES component(component_id);


--
-- Name: bcfield fkfmaq70rmbhnx7k7xpca023449; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT fkfmaq70rmbhnx7k7xpca023449 FOREIGN KEY (screen_id) REFERENCES screen(screen_id);


--
-- Name: component_connection_pack fkfps4iu18noomakkm98yrsxtjg; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY component_connection_pack
    ADD CONSTRAINT fkfps4iu18noomakkm98yrsxtjg FOREIGN KEY (modelconnection_connection_id) REFERENCES component_connection(connection_id);


--
-- Name: bcfield fkgil6ks4vb8eg04bet8kwr3iok; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfield
    ADD CONSTRAINT fkgil6ks4vb8eg04bet8kwr3iok FOREIGN KEY (fieldspecification_field_severity_id) REFERENCES bcfieldseverity(field_severity_id);


--
-- Name: connection_securityparams fkgye3kd6sh6yfi8c9wlxa0nm1u; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY connection_securityparams
    ADD CONSTRAINT fkgye3kd6sh6yfi8c9wlxa0nm1u FOREIGN KEY (connection_id) REFERENCES component_connection(connection_id);


--
-- Name: connection_headerparams fkijjeirqyqb0f3p5s0ff7usqgw; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY connection_headerparams
    ADD CONSTRAINT fkijjeirqyqb0f3p5s0ff7usqgw FOREIGN KEY (connection_id) REFERENCES component_connection(connection_id);


--
-- Name: configuration fkj4pwo9mtyhk16698niv60pm5v; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration
    ADD CONSTRAINT fkj4pwo9mtyhk16698niv60pm5v FOREIGN KEY (configurationpack_configuration_pack_id) REFERENCES configuration_pack(configuration_pack_id);


--
-- Name: configuration_pack fkjeuuq906kked6w7hkv5bq3yvm; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY configuration_pack
    ADD CONSTRAINT fkjeuuq906kked6w7hkv5bq3yvm FOREIGN KEY (application_id) REFERENCES application(application_id);


--
-- Name: screen fkkrdbu1g1hkgatbudqcj8v1ml1; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY screen
    ADD CONSTRAINT fkkrdbu1g1hkgatbudqcj8v1ml1 FOREIGN KEY (application_id) REFERENCES application(application_id);


--
-- Name: bcfieldseverity fkt2e1w9eh74mr8ryiw7eg6nxx2; Type: FK CONSTRAINT; Schema: public; Owner: uixy_user
--

ALTER TABLE ONLY bcfieldseverity
    ADD CONSTRAINT fkt2e1w9eh74mr8ryiw7eg6nxx2 FOREIGN KEY (field_bc_field_id) REFERENCES bcfield(bc_field_id);


--
-- PostgreSQL database dump complete
--

