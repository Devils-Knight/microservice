# Description

This repository is used to store the code I wrote while learning microservices.

# 2. Prepare Environment

## 2.1 Postgres database

I will use docker to install Postgres database, following is the `docker-compose.yaml`.

```yaml
version: "3.9"

services:
  postgres:
    image: postgres:15.2
    container_name: postgres
    ports:
      - "5432:5432"
    environment:
      - "POSTGRES_USER=postgres"
      - "POSTGRES_PASSWORD=postgres"
    volumes:
      - "${PWD}/data:/var/lib/postgresql/data"
```

The table structure and the corresponding data are as follows:

```postgresql
drop table if exists public.organizations;
create table if not exists public.organizations
(
    organization_id text collate pg_catalog."default" not null,
    name            text collate pg_catalog."default",
    contact_name    text collate pg_catalog."default",
    contact_email   text collate pg_catalog."default",
    contact_phone   text collate pg_catalog."default",
    constraint organizations_key primary key (organization_id)
) tablespace pg_default;

alter table public.organizations
    owner to postgres;

drop table if exists public.license;
create table if not exists public.licenses
(
    license_id      text collate pg_catalog."default" not null,
    organization_id text collate pg_catalog."default" not null,
    description     text collate pg_catalog."default",
    product_name    text collate pg_catalog."default" not null,
    license_type    text collate pg_catalog."default" not null,
    comment         text collate pg_catalog."default",
    constraint license_key primary key (license_id),
    constraint license_organization_id_fkey foreign key (organization_id)
        references public.organizations (organization_id) match simple
        on update no action
        on delete no action not deferrable
) tablespace pg_default;

alter table public.licenses
    owner to postgres;

INSERT INTO public.organizations
VALUES ('e6a625cc-718b-48c2-ac76-1dfdff9a531e', 'Ostock', 'Illary Huaylupo', 'illaryhs@gmail.com', '888888888');
INSERT INTO public.organizations
VALUES ('d898a142-de44-466c-8c88-9ceb2c2429d3', 'OptimaGrowth', 'Admin', 'illaryhs@gmail.com', '888888888');
INSERT INTO public.organizations
VALUES ('e839ee96-28de-4f67-bb79-870ca89743a0', 'Ostock', 'Illary Huaylupo', 'illaryhs@gmail.com', '888888888');
INSERT INTO public.licenses
VALUES ('f2a9c9d4-d2c0-44fa-97fe-724d77173c62', 'd898a142-de44-466c-8c88-9ceb2c2429d3', 'Software Product', 'Ostock',
        'complete', 'I AM DEV');
INSERT INTO public.licenses
VALUES ('279709ff-e6d5-4a54-8b55-a5c37542025b', 'e839ee96-28de-4f67-bb79-870ca89743a0', 'Software Product', 'Ostock',
        'complete', 'I AM DEV');
```

## 2.2 Vault

The `docker-compose.yaml` of Vault is as follows:

```yaml
version: "3.9"

services:
  vault:
    container_name: vault
    image: hashicorp/vault:1.13
    ports:
      - "8200:8200"
    environment:
      - "VAULT_DEV_ROOT_TOKEN_ID=root"
```

## 2.3 keycloak

```yaml
version: "3.9"

services:
  keycloak:
    container_name: keycloak
    image: bitnami/keycloak:21.0.2-debian-11-r2
    ports:
      - "10000:8080"
    environment:
      - "KEYCLOAK_ADMIN_USER=admin"
      - "KEYCLOAK_ADMIN_PASSWORD=admin"
      - "KEYCLOAK_MANAGEMENT_USER=admin"
      - "KEYCLOAK_MANAGEMENT_PASSWORD=admin"
      - "KEYCLOAK_DATABASE_VENDOR=dev-file"
```

### What's the client in Keycloak?

Clients in Keycloak are entities that can request user authentication. The clients are often the applications or services that we want to secure by providing a single sign-on (SSO) solution.


### Testing the client

To confirm the client was created successfully, you can use the SPA testing application on the [Keycloak website](https://www.keycloak.org/app/)

### Inspect endpoints

The endpoints located in the `Realm Setting` menu.

### Reference Document

- [Spring Boot Configuration](https://www.keycloak.org/docs/latest/securing_apps/index.html#_spring_boot_adapter)
- [Spring Boot Integration](https://www.keycloak.org/docs/latest/securing_apps/index.html#spring-boot-integration)
- [Java Adapter Configurations](https://www.keycloak.org/docs/latest/securing_apps/index.html#_java_adapter_config)
- [Server Administration Guide](https://keycloak.org/docs/latest/server_admin/)
