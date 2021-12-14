-------------------
-- DB User/PW    --
-- USE: Postgres --
-- PW: 	admin    --
-------------------

CREATE ROLE postgres WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:IuQ14+y2lgBHV519mYMKkQ==$YCJHrAfhR0PGPIOxAmL+CQRVvElB2srT1AeQCAMs1iI=:oH83keytsVmwrEFTMgLWII0RCeMgoTj7mpPK67aW7V0=';

---------------
-- DB Create --
---------------

CREATE DATABASE ipatest
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'German_Switzerland.1252'
    LC_CTYPE = 'German_Switzerland.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


--------------
-- Sequence --
--------------

CREATE SEQUENCE public.ipatest_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1000
	CACHE 1
	NO CYCLE;
	
	
--------------
-- Function --
--------------

CREATE OR REPLACE FUNCTION public.get_ucname(ucuid bigint)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE
    codeName varchar;
BEGIN
    
	select code_name 
	into   codeName 
	from   ipatest_uc
	where  uc_uid = ucUid;
    
    return codeName;
END;
$function$
;


------------
-- Tables --
------------

CREATE TABLE public.error_handling (
	id int8 NOT NULL,
	project varchar NULL,
	error_type varchar NULL,
	error varchar NULL,
	solution varchar NULL,
	CONSTRAINT error_handling_pk PRIMARY KEY (id)
);


CREATE TABLE public.logs (
	id int8 NOT NULL,
	"timestamp" timestamp NULL,
	project varchar NULL,
	"server" varchar NULL,
	message varchar NULL,
	checkbox bool NULL,
	CONSTRAINT logs_pk PRIMARY KEY (id)
);


CREATE TABLE public.ipatest_role_permission (
	role_uid int8 NOT NULL,
	"permission" varchar(250) NOT NULL,
	CONSTRAINT ipatest_role_permission_pk PRIMARY KEY (role_uid, permission)
);


CREATE TABLE public.ipatest_uc (
	uc_uid int8 NOT NULL,
	code_type int8 NULL,
	code_name varchar(60) NULL,
	uc_no float8 NULL,
	description varchar(4000) NULL,
	CONSTRAINT ipatest_uc_pk PRIMARY KEY (uc_uid)
);


CREATE TABLE public.ipatest_user (
	user_nr int8 NOT NULL,
	user_name varchar(60) NULL,
	"password" varchar(250) NULL,
	"token" varchar(60) NULL,
	login varchar(60) NULL,
	email varchar(60) NULL,
	local_user bool NULL,
	CONSTRAINT ipatest_user_pk PRIMARY KEY (user_nr)
);


CREATE TABLE public.ipatest_user_role (
	user_nr int8 NOT NULL,
	role_uid int8 NOT NULL,
	CONSTRAINT ipatest_user_role_pk PRIMARY KEY (user_nr, role_uid)
);


----------------
-- Dummy Data --
----------------

INSERT INTO public.error_handling (id,project,error_type,error,solution) VALUES
	 (3,'EDI','ERROR','[Camel (EdiOutputSendToAS2) thread #1 - timer://AS2Trigger] rEdiBackup - Backup message to: \\fileserver02\Navision\Archiv\EDI\2021\12\A269194\IV_\Competec-Migros-IV_A269194_24797187.edi',NULL),
	 (4,'EDI','WARN','[Camel (EdiOutputSendToAS2) thread #1 - timer://AS2Trigger] rEdiBackup - Backup message to: \\fileserver02\Navision\Archiv\EDI\2021\12\A269194\IV_\Competec-Migros-IV_A269194_24797187.edi',NULL),
	 (5,'MPT','ERROR','2021-12-07 00:47:57,642 ERROR [Camel (camel-1) thread #14023 - Split] org.hibernate.engine.jdbc.spi.SqlExceptionHelper - Violation of PRIMARY KEY constraint ''PK_nav_verkaufsauftrag_kopf''. Cannot insert duplicate key in object ''dbo.nav_verkaufsauftrag_kopf''. The duplicate key value is (Auftrag, 25771275). ',NULL),
	 (6,'PIM','WARN','[http-nio-8080-exec-3468] org.eclipse.scout.rt.server.session.ScoutSessionBindingListener - Unable to remove http session for scout session id 1a9gb9jsppvvgnd63gfhev4h2v3nt8o3256632fb0knphffut1up. - MDC[principal=12290, scoutSession=1a9gb9jsppvvgnd63gfhev4h2v3nt8o3256632fb0knphffut1up, cid=8ff7b4a2-8224-47b0-8f66-29e09a6613a3]',NULL),
	 (8,'EDI','ERROR','[Camel (EdiOutputSendToAS2) thread #1 - timer://AS2Trigger] rEdiBackup - Backup message to: \\fileserver02\Navision\Archiv\EDI\2021\12\A269194\IV_\Competec-Migros-IV_A269194_24797187.edi',NULL),
	 (2,'MPT','WARN','2021-12-07 00:47:57,642 ERROR [Camel (camel-1) thread #14023 - Split] org.hibernate.engine.jdbc.spi.SqlExceptionHelper - Violation of PRIMARY KEY constraint ''PK_nav_verkaufsauftrag_kopf''. Cannot insert duplicate key in object ''dbo.nav_verkaufsauftrag_kopf''. The duplicate key value is (Auftrag, 25771275).','1) Burn it
2) Walk away'),
	 (1,'EDI','ERROR','[Camel (EdiOutputSendToAS2) thread #1 - timer://AS2Trigger] rEdiBackup - Backup message to: \\fileserver02\Navision\Archiv\EDI\2021\12\A269194\IV_\Competec-Migros-IV_A269194_24797187.edi','Solution #1
#2'),
	 (9,'MPT','ERROR','2021-12-07 00:47:57,642 ERROR [Camel (camel-1) thread #14023 - Split] org.hibernate.engine.jdbc.spi.SqlExceptionHelper - Violation of PRIMARY KEY constraint ''PK_nav_verkaufsauftrag_kopf''. Cannot insert duplicate key in object ''dbo.nav_verkaufsauftrag_kopf''. The duplicate key value is (Auftrag, 25771275). ',NULL),
	 (10,'EDI','WARN','[Camel (EdiOutputSendToAS2) thread #1 - timer://AS2Trigger] rEdiBackup - Backup message to: \\fileserver02\Navision\Archiv\EDI\2021\12\A269194\IV_\Competec-Migros-IV_A269194_24797187.edi',NULL),
	 (1000,'TEST','ERROR','dasdsa',NULL);
INSERT INTO public.error_handling (id,project,error_type,error,solution) VALUES
	 (1004,'TEST','WARN','Why u no work as I want! :( ?','PLS WORK!'),
	 (7,'PIM','ERROR','[http-nio-8080-exec-3468] org.eclipse.scout.rt.server.session.ScoutSessionBindingListener - Unable to remove http session for scout session id 1a9gb9jsppvvgnd63gfhev4h2v3nt8o3256632fb0knphffut1up. - MDC[principal=12290, scoutSession=1a9gb9jsppvvgnd63gfhev4h2v3nt8o3256632fb0knphffut1up, cid=8ff7b4a2-8224-47b0-8f66-29e09a6613a3]','fddsfdsfsdfdgsdf');
	 

INSERT INTO public.ipatest_role_permission (role_uid,"permission") VALUES
	 (1005,'CopyToClipboardPermission'),
	 (1005,'CreateChangePasswordPermission'),
	 (1005,'CreateCodePermission'),
	 (1005,'CreateCustomColumnPermission'),
	 (1005,'CreateErrorHandlerPermission'),
	 (1005,'CreateGlobalBookmarkPermission'),
	 (1005,'CreateLogHandlerPermission'),
	 (1005,'CreateRolePermissionPermission'),
	 (1005,'CreateUserBookmarkPermission'),
	 (1005,'CreateUserPermission');
INSERT INTO public.ipatest_role_permission (role_uid,"permission") VALUES
	 (1005,'DeleteCustomColumnPermission'),
	 (1005,'DeleteGlobalBookmarkPermission'),
	 (1005,'DeleteUserBookmarkPermission'),
	 (1005,'DeleteUserPermission'),
	 (1005,'PublishUserBookmarkPermission'),
	 (1005,'ReadChangePasswordPermission'),
	 (1005,'ReadCodePermission'),
	 (1005,'ReadDiagnosticServletPermission'),
	 (1005,'ReadErrorHandlerPermission'),
	 (1005,'ReadGlobalBookmarkPermission');
INSERT INTO public.ipatest_role_permission (role_uid,"permission") VALUES
	 (1005,'ReadLogHandlerPermission'),
	 (1005,'ReadRolePermissionPermission'),
	 (1005,'ReadUserBookmarkPermission'),
	 (1005,'ReadUserPermission'),
	 (1005,'UpdateChangePasswordPermission'),
	 (1005,'UpdateCodePermission'),
	 (1005,'UpdateCustomColumnPermission'),
	 (1005,'UpdateDiagnosticServletPermission'),
	 (1005,'UpdateErrorHandlerPermission'),
	 (1005,'UpdateGlobalBookmarkPermission');
INSERT INTO public.ipatest_role_permission (role_uid,"permission") VALUES
	 (1005,'UpdateLogHandlerPermission'),
	 (1005,'UpdateRolePermissionPermission'),
	 (1005,'UpdateServiceConfigurationPermission'),
	 (1005,'UpdateUserBookmarkPermission'),
	 (1005,'UpdateUserPermission'),
	 (1014,'CopyToClipboardPermission'),
	 (1014,'CreateErrorHandlerPermission'),
	 (1014,'CreateLogHandlerPermission'),
	 (1014,'ReadChangePasswordPermission'),
	 (1014,'ReadCodePermission');
INSERT INTO public.ipatest_role_permission (role_uid,"permission") VALUES
	 (1014,'ReadDiagnosticServletPermission'),
	 (1014,'ReadErrorHandlerPermission'),
	 (1014,'ReadGlobalBookmarkPermission'),
	 (1014,'ReadLogHandlerPermission'),
	 (1014,'ReadRolePermissionPermission'),
	 (1014,'ReadUserBookmarkPermission'),
	 (1014,'ReadUserPermission'),
	 (1014,'UpdateErrorHandlerPermission'),
	 (1014,'UpdateLogHandlerPermission');
	 
	 
INSERT INTO public.ipatest_uc (uc_uid,code_type,code_name,uc_no,description) VALUES
	 (20,1,'permission',NULL,'Role Permission'),
	 (1005,20,'Administrator',NULL,'Full Access / All Permissions'),
	 (1014,20,'User',NULL,'View/Read/Create');	 
	 
	 
INSERT INTO public.ipatest_user (user_nr,user_name,"password","token",login,email,local_user) VALUES
	 (1000,'Admin','adminadmin1234','ADMIN','ADMIN','admin@admin.ch',true),
	 (1013,'User','useruser1234','USER','USER','user@user.gov',true);
	 
	 
INSERT INTO public.ipatest_user_role (user_nr,role_uid) VALUES
	 (1000,1005);
	 
	 
INSERT INTO public.logs (id,"timestamp",project,"server",message,checkbox) VALUES
	 (20,'2021-08-02 09:00:00.000','PIM','\\SERVER01','Violation of PRIMARY KEY constraint ''PK_nav_verkaufsauftrag_kopf''. Cannot insert duplicate key in object ''dbo.nav_verkaufsauftrag_kopf''. The duplicate key value is (Auftrag, 25771276).',true),
	 (22,'2020-11-26 21:50:00.000','MPT','\\MPT02','SQL Error: 2627, SQLState: 23000 ',false),
	 (21,'2021-08-02 09:01:32.000','MPT','\\MPT01','org.hibernate.exception.ConstraintViolationException: could not execute statement - Faulty Message: ch.competec.mdm.dwh.pojos.VerkaufsauftragKopf@def836a',true);