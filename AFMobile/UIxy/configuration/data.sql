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

SET search_path = public, pg_catalog;

--
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY application (application_id, application_name, consumer_context_path, consumer_hostname, consumer_port, consumer_protocol, proxy_hostname, proxy_port, proxy_protocol, remote_hostname, remote_port, remote_protocol, application_uuid) FROM stdin;
\.


--
-- Data for Name: business_case; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_case (business_case_id, description, name, application_id) FROM stdin;
\.


--
-- Data for Name: configuration_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration_pack (configuration_pack_id, pack_name, application_id) FROM stdin;
\.


--
-- Data for Name: business_phase; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_phase (phase_id, classification_unit, phase_name, scoringunit, business_case_id, configuration_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: component_connection; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection (connection_id, address, parameters, port, protocol, real_address, real_parameters, real_port, real_protocol) FROM stdin;
\.


--
-- Data for Name: component_connection_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection_pack (connection_pack_id, dataconnection_connection_id, modelconnection_connection_id, sendconnection_connection_id) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component (component_id, field_info_hostname, field_info_parameters, field_info_port, field_info_protocol, name, type, application_id, proxyconnections_connection_pack_id) FROM stdin;
\.


--
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY field (field_id, class_name, field_name, field_type, value) FROM stdin;
\.


--
-- Data for Name: screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY screen (screen_id, key, menu_order, name, url, application_id, phase_id) FROM stdin;
\.


--
-- Data for Name: bcfield; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfield (bc_field_id, component_id, field_id, fieldspecification_field_severity_id, phase_id, screen_id) FROM stdin;
\.


--
-- Data for Name: bcfieldseverity; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfieldseverity (field_severity_id, purpose, score, severity, field_bc_field_id) FROM stdin;
\.


--
-- Data for Name: component_screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_screen (component_id, screen_id) FROM stdin;
\.


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration (configuration_id, behavior, threshold_end, threshold_start, configurationpack_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: connection_headerparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_headerparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Data for Name: connection_securityparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_securityparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('application_application_id_seq', 1, false);


--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfield_bc_field_id_seq', 1, false);


--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfieldseverity_field_severity_id_seq', 1, false);


--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_case_business_case_id_seq', 1, false);


--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_phase_phase_id_seq', 1, false);


--
-- Name: component_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_component_id_seq', 1, false);


--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_connection_id_seq', 1, false);


--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_pack_connection_pack_id_seq', 1, false);


--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_configuration_id_seq', 1, false);


--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_pack_configuration_pack_id_seq', 1, false);


--
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('field_field_id_seq', 1, false);


--
-- Name: screen_screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('screen_screen_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

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

SET search_path = public, pg_catalog;

--
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY application (application_id, application_name, consumer_context_path, consumer_hostname, consumer_port, consumer_protocol, proxy_hostname, proxy_port, proxy_protocol, remote_hostname, remote_port, remote_protocol, application_uuid) FROM stdin;
\.


--
-- Data for Name: business_case; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_case (business_case_id, description, name, application_id) FROM stdin;
\.


--
-- Data for Name: configuration_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration_pack (configuration_pack_id, pack_name, application_id) FROM stdin;
\.


--
-- Data for Name: business_phase; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_phase (phase_id, classification_unit, phase_name, scoringunit, business_case_id, configuration_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: component_connection; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection (connection_id, address, parameters, port, protocol, real_address, real_parameters, real_port, real_protocol) FROM stdin;
\.


--
-- Data for Name: component_connection_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection_pack (connection_pack_id, dataconnection_connection_id, modelconnection_connection_id, sendconnection_connection_id) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component (component_id, field_info_hostname, field_info_parameters, field_info_port, field_info_protocol, name, type, application_id, proxyconnections_connection_pack_id) FROM stdin;
\.


--
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY field (field_id, class_name, field_name, field_type, value) FROM stdin;
\.


--
-- Data for Name: screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY screen (screen_id, key, menu_order, name, url, application_id, phase_id) FROM stdin;
\.


--
-- Data for Name: bcfield; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfield (bc_field_id, component_id, field_id, fieldspecification_field_severity_id, phase_id, screen_id) FROM stdin;
\.


--
-- Data for Name: bcfieldseverity; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfieldseverity (field_severity_id, purpose, score, severity, field_bc_field_id) FROM stdin;
\.


--
-- Data for Name: component_screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_screen (component_id, screen_id) FROM stdin;
\.


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration (configuration_id, behavior, threshold_end, threshold_start, configurationpack_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: connection_headerparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_headerparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Data for Name: connection_securityparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_securityparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('application_application_id_seq', 1, false);


--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfield_bc_field_id_seq', 1, false);


--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfieldseverity_field_severity_id_seq', 1, false);


--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_case_business_case_id_seq', 1, false);


--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_phase_phase_id_seq', 1, false);


--
-- Name: component_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_component_id_seq', 1, false);


--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_connection_id_seq', 1, false);


--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_pack_connection_pack_id_seq', 1, false);


--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_configuration_id_seq', 1, false);


--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_pack_configuration_pack_id_seq', 1, false);


--
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('field_field_id_seq', 1, false);


--
-- Name: screen_screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('screen_screen_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

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

SET search_path = public, pg_catalog;

--
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY application (application_id, application_name, consumer_context_path, consumer_hostname, consumer_port, consumer_protocol, proxy_hostname, proxy_port, proxy_protocol, remote_hostname, remote_port, remote_protocol, application_uuid) FROM stdin;
\.


--
-- Data for Name: bcfieldseverity; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfieldseverity (field_severity_id, purpose, score, severity) FROM stdin;
\.


--
-- Data for Name: business_case; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_case (business_case_id, description, name, application_id) FROM stdin;
\.


--
-- Data for Name: configuration_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration_pack (configuration_pack_id, pack_name, application_id) FROM stdin;
\.


--
-- Data for Name: business_phase; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_phase (phase_id, classification_unit, phase_name, scoringunit, business_case_id, configuration_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: component_connection; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection (connection_id, address, parameters, port, protocol, real_address, real_parameters, real_port, real_protocol) FROM stdin;
\.


--
-- Data for Name: component_connection_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection_pack (connection_pack_id, dataconnection_connection_id, modelconnection_connection_id, sendconnection_connection_id) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component (component_id, field_info_hostname, field_info_parameters, field_info_port, field_info_protocol, name, type, application_id, proxyconnections_connection_pack_id) FROM stdin;
\.


--
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY field (field_id, class_name, field_name, field_type, value) FROM stdin;
\.


--
-- Data for Name: screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY screen (screen_id, key, menu_order, name, url, application_id, phase_id) FROM stdin;
\.


--
-- Data for Name: bcfield; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfield (bc_field_id, component_id, field_id, fieldspecification_field_severity_id, phase_id, screen_id) FROM stdin;
\.


--
-- Data for Name: component_screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_screen (component_id, screen_id) FROM stdin;
\.


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration (configuration_id, behavior, threshold_end, threshold_start, configurationpack_configuration_pack_id) FROM stdin;
\.


--
-- Data for Name: connection_headerparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_headerparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Data for Name: connection_securityparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_securityparams (connection_id, param_value, param_name) FROM stdin;
\.


--
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('application_application_id_seq', 1, false);


--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfield_bc_field_id_seq', 1, false);


--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfieldseverity_field_severity_id_seq', 1, false);


--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_case_business_case_id_seq', 1, false);


--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_phase_phase_id_seq', 1, false);


--
-- Name: component_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_component_id_seq', 1, false);


--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_connection_id_seq', 1, false);


--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_pack_connection_pack_id_seq', 1, false);


--
-- Name: configuration_configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_configuration_id_seq', 1, false);


--
-- Name: configuration_pack_configuration_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('configuration_pack_configuration_pack_id_seq', 1, false);


--
-- Name: field_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('field_field_id_seq', 1, false);


--
-- Name: screen_screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('screen_screen_id_seq', 1, false);


--
-- PostgreSQL database dump complete
--

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

SET search_path = public, pg_catalog;

--
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY application (application_id, application_name, consumer_context_path, consumer_hostname, consumer_port, consumer_protocol, proxy_hostname, proxy_port, proxy_protocol, remote_hostname, remote_port, remote_protocol, application_uuid) FROM stdin;
1	Showcase	/NSRest	localhost	8080	http	81.2.216.197	8080	http	localhost	8080	http	4c7649c1-45b2-40f3-8b94-98fba303c1f0
\.


--
-- Data for Name: bcfieldseverity; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfieldseverity (field_severity_id, purpose, score, severity) FROM stdin;
1	SYSTEM_IDENTIFICATION	\N	CRITICAL
2	SYSTEM_IDENTIFICATION	\N	CRITICAL
3	SYSTEM_INFORMATION	\N	REQUIRED
4	SYSTEM_INFORMATION	\N	REQUIRED
5	INFORMATION_MINING	\N	NICE_TO_HAVE
6	SYSTEM_INFORMATION	\N	REQUIRED
7	SYSTEM_INFORMATION	\N	REQUIRED
8	SYSTEM_INFORMATION	\N	REQUIRED
9	SYSTEM_INFORMATION	\N	REQUIRED
10	SYSTEM_INFORMATION	\N	CRITICAL
11	FUTURE_INTERACTION	\N	REQUIRED
12	SYSTEM_INFORMATION	\N	REQUIRED
13	INFORMATION_MINING	\N	NICE_TO_HAVE
14	INFORMATION_MINING	\N	NICE_TO_HAVE
15	SYSTEM_IDENTIFICATION	\N	CRITICAL
16	SYSTEM_INFORMATION	\N	REQUIRED
17	FUTURE_INTERACTION	\N	NEEDED
18	FUTURE_INTERACTION	\N	NEEDED
19	SYSTEM_INFORMATION	\N	REQUIRED
20	INFORMATION_MINING	\N	NICE_TO_HAVE
21	SYSTEM_INFORMATION	\N	REQUIRED
\.


--
-- Data for Name: business_case; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_case (business_case_id, description, name, application_id) FROM stdin;
1	Profile management ...	Profile management	1
2	management of vehicles	Vehicle management	1
\.


--
-- Data for Name: configuration_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY configuration_pack (configuration_pack_id, pack_name, application_id) FROM stdin;
1	Default config	1
\.


--
-- Data for Name: business_phase; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY business_phase (phase_id, classification_unit, phase_name, scoringunit, business_case_id, configuration_configuration_pack_id) FROM stdin;
2	BASIC	Profile edition	NEARBY_DEVICE_SCORING	1	1
3	BASIC	Edit vehicle	BATTERY_AND_CONNECTION_SCORING	2	1
\.


--
-- Data for Name: component_connection; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection (connection_id, address, parameters, port, protocol, real_address, real_parameters, real_port, real_protocol) FROM stdin;
1	81.2.216.197	/UIxy/api/connections/model/component/1	8080	http	localhost	/AFServer/rest/country/definition	8080	http
2	81.2.216.197	/UIxy/api/connections/send/component/1	8080	http	localhost	/AFServer/rest/country	8080	http
3	81.2.216.197	/UIxy/api/connections/model/component/2	8080	http	localhost	/AFServer/rest/country/definition	8080	http
4	81.2.216.197	/UIxy/api/connections/data/component/2	8080	http	localhost	/AFServer/rest/country/list	8080	http
5	81.2.216.197	/UIxy/api/connections/model/component/3	8080	http	localhost	/AFServer/rest/country/definition	8080	http
6	81.2.216.197	/UIxy/api/connections/data/component/3	8080	http	localhost	/AFServer/rest/country/list	8080	http
7	81.2.216.197	/UIxy/api/connections/model/component/4	8080	http	localhost	/AFServer/rest/login/definition	8080	http
8	81.2.216.197	/UIxy/api/connections/send/component/4	8080	http	localhost	/AFServer/rest/login/	8080	http
9	81.2.216.197	/UIxy/api/connections/model/component/5	8080	http	localhost	/AFServer/rest/vehicle/definition	8080	http
10	81.2.216.197	/UIxy/api/connections/data/component/5	8080	http	localhost	/AFServer/rest/vehicle/list	8080	http
11	81.2.216.197	/UIxy/api/connections/model/component/6	8080	http	localhost	/AFServer/rest/vehicle/definition	8080	http
12	81.2.216.197	/UIxy/api/connections/send/component/6	8080	http	localhost	/AFServer/rest/vehicle	8080	http
13	81.2.216.197	/UIxy/api/connections/model/component/7	8080	http	localhost	/AFServer/rest/vehicle/definition	8080	http
14	81.2.216.197	/UIxy/api/connections/data/component/7	8080	http	localhost	/AFServer/rest/vehicle/list	8080	http
15	81.2.216.197	/UIxy/api/connections/model/component/8	8080	http	localhost	/AFServer/rest/users/profile	8080	http
16	81.2.216.197	/UIxy/api/connections/data/component/8	8080	http	localhost	/AFServer/rest/users/user/#{username}	8080	http
17	81.2.216.197	/UIxy/api/connections/send/component/8	8080	http	localhost	/AFServer/rest/users/update	8080	http
18	81.2.216.197	/UIxy/api/connections/model/component/9	8080	http	localhost	/AFServer/rest/absencetype/definition/supportedCountries	8080	http
19	81.2.216.197	/UIxy/api/connections/model/component/10	8080	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
20	81.2.216.197	/UIxy/api/connections/data/component/10	8080	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
21	81.2.216.197	/UIxy/api/connections/model/component/11	8080	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
22	81.2.216.197	/UIxy/api/connections/send/component/11	8080	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
23	81.2.216.197	/UIxy/api/connections/model/component/12	8080	http	localhost	/AFServer/rest/absenceInstance/definitionAdd/#{user}	8080	http
24	81.2.216.197	/UIxy/api/connections/send/component/12	8080	http	localhost	/AFServer/rest/absenceInstance/add/#{user}	8080	http
25	81.2.216.197	/UIxy/api/connections/model/component/13	8080	http	localhost	/AFServer/rest/absenceInstance/definition/#{username}	8080	http
26	81.2.216.197	/UIxy/api/connections/data/component/13	8080	http	localhost	/AFServer/rest/absenceInstance/user/#{username}	8080	http
27	81.2.216.197	/UIxy/api/connections/model/component/14	8080	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
28	81.2.216.197	/UIxy/api/connections/data/component/14	8080	http	localhost	/AFServer/rest/absenceInstance/editable/#{username}	8080	http
29	81.2.216.197	/UIxy/api/connections/model/component/15	8080	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
30	81.2.216.197	/UIxy/api/connections/data/component/15	8080	http	localhost	/AFServer/rest/absenceInstance/add/#{username}	8080	http
31	81.2.216.197	/UIxy/api/connections/model/component/16	8080	http	localhost	/AFServer/rest/businessTrip/definition/	8080	http
32	81.2.216.197	/UIxy/api/connections/data/component/16	8080	http	localhost	/AFServer/rest/businessTrip/user/#{username}	8080	http
33	81.2.216.197	/UIxy/api/connections/model/component/17	8080	http	localhost	/AFServer/rest/businessTrip/definitionAdd/	8080	http
34	81.2.216.197	/UIxy/api/connections/send/component/17	8080	http	localhost	/AFServer/rest/businessTrip/add/#{username}	8080	http
35	81.2.216.197	/UIxy/api/connections/model/component/18	8080	http	localhost	/AFServer/rest/businessTripPart/definition/	8080	http
36	81.2.216.197	/UIxy/api/connections/data/component/18	8080	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/list/#{businessTripId}	8080	http
37	81.2.216.197	/UIxy/api/connections/model/component/19	8080	http	localhost	/AFServer/rest/businessTripPart/definitionAdd/	8080	http
38	81.2.216.197	/UIxy/api/connections/send/component/19	8080	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/add/#{businessTripId}	8080	http
39	81.2.216.197	/UIxy/api/connections/model/component/20	8080	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
40	81.2.216.197	/UIxy/api/connections/data/component/20	8080	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
41	81.2.216.197	/UIxy/api/connections/model/component/21	8080	http	localhost	/AFServer/rest/absenceInstance/definition/#{username}	8080	http
42	81.2.216.197	/UIxy/api/connections/data/component/21	8080	http	localhost	/AFServer/rest/absenceInstance/user/#{username}	8080	http
43	81.2.216.197	/UIxy/api/connections/model/component/22	8080	http	localhost	/AFServer/rest/absenceInstance/definitionManaged/#{username}	8080	http
44	81.2.216.197	/UIxy/api/connections/data/component/22	8080	http	localhost	/AFServer/rest/absenceInstance/editable/#{username}	8080	http
45	81.2.216.197	/UIxy/api/connections/model/component/23	8080	http	localhost	/AFServer/rest/businessTrip/definition/	8080	http
46	81.2.216.197	/UIxy/api/connections/data/component/23	8080	http	localhost	/AFServer/rest/businessTrip/user/#{username}	8080	http
47	81.2.216.197	/UIxy/api/connections/model/component/24	8080	http	localhost	/AFServer/rest/businessTripPart/definition/	8080	http
48	81.2.216.197	/UIxy/api/connections/data/component/24	8080	http	localhost	/AFServer/rest/businessTripPart/user/#{username}/list/#{businessTripId}	8080	http
49	81.2.216.197	/UIxy/api/connections/model/component/25	8080	http	localhost	/AFServer/rest/absencetype/definition/	8080	http
50	81.2.216.197	/UIxy/api/connections/data/component/25	8080	http	localhost	/AFServer/rest/absencetype/country/#{id}	8080	http
\.


--
-- Data for Name: component_connection_pack; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_connection_pack (connection_pack_id, dataconnection_connection_id, modelconnection_connection_id, sendconnection_connection_id) FROM stdin;
1	\N	1	2
2	4	3	\N
3	6	5	\N
4	\N	7	8
5	10	9	\N
6	\N	11	12
7	14	13	\N
8	16	15	17
9	\N	18	\N
10	20	19	\N
11	\N	21	22
12	\N	23	24
13	26	25	\N
14	28	27	\N
15	30	29	\N
16	32	31	\N
17	\N	33	34
18	36	35	\N
19	\N	37	38
20	40	39	\N
21	42	41	\N
22	44	43	\N
23	46	45	\N
24	48	47	\N
25	50	49	\N
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component (component_id, field_info_hostname, field_info_parameters, field_info_port, field_info_protocol, name, type, application_id, proxyconnections_connection_pack_id) FROM stdin;
1	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryForm	FORM	1	1
2	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryTable	TABLE	1	2
3	localhost	/AFServer/rest/country/fieldInfo	8080	http	countryList	LIST	1	3
4	localhost	/AFServer/rest/login/fieldInfo	8080	http	loginForm	FORM	1	4
5	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesTable	TABLE	1	5
6	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesForm	FORM	1	6
7	localhost	/AFServer/rest/vehicle/fieldInfo	8080	http	vehiclesList	LIST	1	7
8	localhost	/AFServer/rest/users/fieldInfo	8080	http	personProfileForm	FORM	1	8
9	localhost	/AFServer/rest/absencetype/supportedCountries/fieldInfo	8080	http	absenceCountryForm	FORM	1	9
11	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeForm	FORM	1	11
12	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceForm	FORM	1	12
13	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceTable	TABLE	1	13
14	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditTable	TABLE	1	14
15	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditForm	FORM	1	15
16	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripTable	TABLE	1	16
17	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripForm	FORM	1	17
18	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartTable	TABLE	1	18
19	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartForm	FORM	1	19
20	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeList	LIST	1	20
21	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceList	LIST	1	21
22	localhost	/AFServer/rest/absenceInstance/fieldInfo	8080	http	absenceInstanceEditList	LIST	1	22
23	localhost	/AFServer/rest/businessTrip/fieldInfo	8080	http	businessTripList	LIST	1	23
24	localhost	/AFServer/rest/businessTripPart/fieldInfo	8080	http	businessTripPartList	LIST	1	24
25	localhost	/AFServer/rest/absencetype/fieldInfo	8080	http	absenceTypeTable	TABLE	1	25
\.


--
-- Data for Name: field; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY field (field_id, class_name, field_name, field_type, value) FROM stdin;
1	person	login	TEXTFIELD	\N
2	person	password	PASSWORD	\N
3	person	firstName	TEXTFIELD	\N
4	person	lastName	TEXTFIELD	\N
5	person	age	NUMBERFIELD	\N
6	person	myAddress.street	TEXTFIELD	\N
7	person	myAddress.city	TEXTFIELD	\N
8	person	myAddress.postCode	NUMBERFIELD	\N
9	person	myAddress.country	DROPDOWNMENU	\N
10	person	active	OPTION	\N
11	person	confidentialAgreement	CHECKBOX	\N
12	person	email	TEXTFIELD	\N
13	person	gender	OPTION	\N
14	person	hireDate	CALENDAR	\N
15	vehicle	name	TEXTFIELD	\N
16	vehicle	vehicleType	DROPDOWNMENU	\N
17	vehicle	fuelConsumption	NUMBERDOUBLEFIELD	\N
18	vehicle	fuelType	DROPDOWNMENU	\N
19	vehicle	tachometerKilometers	NUMBERDOUBLEFIELD	\N
20	vehicle	note	TEXTFIELD	\N
21	vehicle	available	DROPDOWNMENU	\N
\.


--
-- Data for Name: screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY screen (screen_id, key, menu_order, name, url, application_id, phase_id) FROM stdin;
1	Login	1	Login	http://81.2.216.197:8080/UIxy/api/screens/1	1	\N
2	Supported countries	2	Supported countries	http://81.2.216.197:8080/UIxy/api/screens/2	1	\N
5	Absence type management	5	Absence type management	http://81.2.216.197:8080/UIxy/api/screens/5	1	\N
6	Create absence	6	Create Absence	http://81.2.216.197:8080/UIxy/api/screens/6	1	\N
7	My absences	7	My absences	http://81.2.216.197:8080/UIxy/api/screens/7	1	\N
8	Absence management	8	Absence management	http://81.2.216.197:8080/UIxy/api/screens/8	1	\N
9	Business Trips	9	Business Trips	http://81.2.216.197:8080/UIxy/api/screens/9	1	\N
4	Profile	4	My profile	http://81.2.216.197:8080/UIxy/api/screens/4	1	2
3	Vehicle management	3	Vehicle management	http://81.2.216.197:8080/UIxy/api/screens/3	1	3
\.


--
-- Data for Name: bcfield; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY bcfield (bc_field_id, component_id, field_id, fieldspecification_field_severity_id, phase_id, screen_id) FROM stdin;
1	8	1	1	2	4
2	8	2	2	2	4
3	8	3	3	2	4
4	8	4	4	2	4
5	8	5	5	2	4
6	8	6	6	2	4
7	8	7	7	2	4
8	8	8	8	2	4
9	8	9	9	2	4
10	8	10	10	2	4
11	8	11	11	2	4
12	8	12	12	2	4
13	8	13	13	2	4
14	8	14	14	2	4
15	7	15	15	3	3
16	7	16	16	3	3
17	7	17	17	3	3
18	7	18	18	3	3
19	7	19	19	3	3
20	7	20	20	3	3
21	7	21	21	3	3
\.


--
-- Data for Name: component_screen; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY component_screen (component_id, screen_id) FROM stdin;
1	4
2	1
2	2
2	3
3	7
3	5
3	6
4	8
5	9
5	11
5	20
5	25
6	12
7	13
7	21
8	15
8	14
8	22
9	24
9	23
9	19
9	18
9	16
9	17
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
-- Data for Name: connection_headerparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_headerparams (connection_id, param_value, param_name) FROM stdin;
1	application/json	content-type
3	application/json	content-type
4	application/json	content-type
5	application/json	content-type
6	application/json	content-type
7	application/json	content-type
8	application/json	content-type
9	application/json	content-type
10	application/json	content-type
11	application/json	content-type
12	application/json	content-type
13	application/json	content-type
14	application/json	content-type
15	application/json	content-type
18	application/json	content-type
19	application/json	content-type
21	application/json	content-type
23	application/json	content-type
25	application/json	content-type
27	application/json	content-type
29	application/json	content-type
31	application/json	content-type
33	application/json	content-type
35	application/json	content-type
37	application/json	content-type
39	application/json	content-type
41	application/json	content-type
43	application/json	content-type
45	application/json	content-type
47	application/json	content-type
49	application/json	content-type
\.


--
-- Data for Name: connection_securityparams; Type: TABLE DATA; Schema: public; Owner: uixy_user
--

COPY connection_securityparams (connection_id, param_value, param_name) FROM stdin;
1	#{password}	password
1	basic	securityMethod
1	#{username}	username
2	#{password}	password
2	basic	securityMethod
2	#{username}	username
3	#{password}	password
3	basic	securityMethod
3	#{username}	username
5	#{password}	password
5	basic	securityMethod
5	#{username}	username
9	#{password}	password
9	basic	securityMethod
9	#{username}	username
11	#{password}	password
11	basic	securityMethod
11	#{username}	username
12	#{password}	password
12	basic	securityMethod
12	#{username}	username
13	#{password}	password
13	basic	securityMethod
13	#{username}	username
16	#{password}	password
16	basic	securityMethod
16	#{username}	username
17	#{password}	password
17	basic	securityMethod
17	#{username}	username
22	#{password}	password
22	basic	securityMethod
22	#{username}	username
23	#{password}	password
23	basic	securityMethod
23	#{username}	username
24	#{password}	password
24	basic	securityMethod
24	#{username}	username
25	#{password}	password
25	basic	securityMethod
25	#{username}	username
26	#{password}	password
26	basic	securityMethod
26	#{username}	username
27	#{password}	password
27	basic	securityMethod
27	#{username}	username
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
36	#{password}	password
36	basic	securityMethod
36	#{username}	username
37	#{password}	password
37	basic	securityMethod
37	#{username}	username
38	#{password}	password
38	basic	securityMethod
38	#{username}	username
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
-- Name: application_application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('application_application_id_seq', 1, true);


--
-- Name: bcfield_bc_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfield_bc_field_id_seq', 21, true);


--
-- Name: bcfieldseverity_field_severity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('bcfieldseverity_field_severity_id_seq', 21, true);


--
-- Name: business_case_business_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_case_business_case_id_seq', 2, true);


--
-- Name: business_phase_phase_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('business_phase_phase_id_seq', 3, true);


--
-- Name: component_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_component_id_seq', 25, true);


--
-- Name: component_connection_connection_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_connection_id_seq', 50, true);


--
-- Name: component_connection_pack_connection_pack_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('component_connection_pack_connection_pack_id_seq', 25, true);


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

SELECT pg_catalog.setval('field_field_id_seq', 21, true);


--
-- Name: screen_screen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: uixy_user
--

SELECT pg_catalog.setval('screen_screen_id_seq', 9, true);


--
-- PostgreSQL database dump complete
--

