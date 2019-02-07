create table psp_product_specification (
	id binary(16) not null,
	content_id binary(16),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	item_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	revision integer not null,
	status varchar(20),
	primary key (id)
) engine=InnoDB;

create table psp_product_specification_content (
	id binary(16) not null,
	barcode_number varchar(100),
	blue_print_id binary(16),
	committed_date datetime,
	committer_id varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	description longtext,
	image_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	revision integer not null,
	specification_id binary(16),
	primary key (id)
) engine=InnoDB;

create table psp_product_specification_content_process (
	id binary(16) not null,
	content_id binary(16),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	process_id binary(16),
	process_info longtext,
	primary key (id)
) engine=InnoDB;

alter table psp_product_specification
	add constraint UKr43334tp78x0i6bbnqoj1onpq unique (item_id);

create index IDXq6aixqve188i4jpj7iuvwx68u
	on psp_product_specification_content (specification_id);

alter table psp_product_specification_content_process
	add constraint UK5u401e2he0ivsbihwie0svpd4 unique (content_id,process_id);
