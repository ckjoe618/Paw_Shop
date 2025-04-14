CREATE TABLE member (
	member_id			INT					IDENTITY(1,1) CONSTRAINT member_memberId_PK PRIMARY KEY,
	member_name			NVARCHAR(30)		NOT NULL,
	gender				VARCHAR(6)			NOT NULL,
	idno				VARCHAR(10)			CONSTRAINT member_idno_UK UNIQUE,
	email				NVARCHAR(100)		CONSTRAINT member_email_UK UNIQUE,
	phone				VARCHAR(10)			CONSTRAINT member_phone_UK UNIQUE,
	birth_date			DATE				NOT NULL,
	account				VARCHAR(50)			NOT NULL,
	password			VARCHAR(20)			NOT NULL,
	create_account_date	DATETIME			CONSTRAINT member_createAccount_DF DEFAULT GETDATE(),
	active_status		BIT					CONSTRAINT member_isActive_DF DEFAULT 1
);

CREATE TABLE article (
    article_id			INT					IDENTITY(1,1) PRIMARY KEY,
    member_id			INT					NOT NULL,
    title               NVARCHAR(255)		NOT NULL,
    content             TEXT				NOT NULL,
    category_id         INT					NOT NULL,
    view_count_num      INT					DEFAULT 0,
    is_pinned_status    BIT					DEFAULT 0,
    is_featured_status  BIT					DEFAULT 0,
    created_date        DATETIME2			DEFAULT CURRENT_TIMESTAMP,
    updated_date        DATETIME2			DEFAULT CURRENT_TIMESTAMP,
    is_deleted_status   BIT					DEFAULT 0
);

CREATE TABLE owner (
    member_id			INT					PRIMARY KEY,
    member_name			VARCHAR(50)			NOT NULL,
    phone_number		VARCHAR(15)			NOT NULL, 
    email				VARCHAR(100)		NOT NULL UNIQUE,
    addr				VARCHAR(255)		NOT NULL,
    join_date			DATE				NOT NULL
);

CREATE TABLE pets (
    pet_id				INT					IDENTITY(1,1) PRIMARY KEY,
	member_id			INT,
    pet_name			VARCHAR(30)			NOT NULL,
    pet_type			VARCHAR(30)			NOT NULL,
    pet_size			VARCHAR(30)			NOT NULL,
    pet_fur				VARCHAR(30)			NOT NULL,
	pet_status			TINYINT				NOT NULL
	FOREIGN KEY (member_id) REFERENCES owner(member_id)
);

CREATE TABLE appointments (
    appointment_id		INT					IDENTITY(1,1) PRIMARY KEY,
    member_id			INT					NOT NULL,
    pet_id				INT					NOT NULL,
    appointment_date	DATE				NOT NULL,
    appointment_timeslot NVARCHAR(50)		NOT NULL,
    appointment_total	INT					NOT NULL DEFAULT 0,
    appointment_status	TINYINT				NOT NULL DEFAULT 0,
    payment_status		TINYINT				NOT NULL DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES owner(member_id),
    FOREIGN KEY (pet_id)	REFERENCES pets(pet_id)
);

CREATE TABLE items (
    item_id				INT					IDENTITY(1,1) PRIMARY KEY,  
    item_name			NVARCHAR(50)		NOT NULL,
    item_price			INT					NOT NULL
);

CREATE TABLE item_details (
    item_detail_id		INT					IDENTITY(1,1) PRIMARY KEY,
    appointment_id		INT					NOT NULL,
    item_id				INT					NOT NULL,
    item_detail_quantity INT				NOT NULL DEFAULT 1,
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id),
    FOREIGN KEY (item_id)		 REFERENCES items(item_id)
);

CREATE TABLE packages (
    package_id			INT					IDENTITY(1,1) PRIMARY KEY,
    package_name		VARCHAR(10)			NOT NULL,
    package_description VARCHAR(20),
    package_price		INT					NOT NULL
);

CREATE TABLE package_details (
	package_details_id	INT					IDENTITY(1,1) PRIMARY KEY,
    appointment_id		INT					NOT NULL,
    package_id			INT					NOT NULL,
    package_details_quantity INT			NOT NULL DEFAULT 1,
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id),
    FOREIGN KEY (package_id)	 REFERENCES packages(package_id)
);

CREATE TABLE product(
    product_id			INT					PRIMARY KEY IDENTITY(1,1),
    product_name		NVARCHAR(200)		NOT NULL,
    product_des			NVARCHAR(1000)		NOT NULL,
    product_price		INT					NOT NULL CHECK (product_price >= 0),
    product_stock		INT					NOT NULL CHECK (product_stock >= 0),
    category_name		VARCHAR(200)		NOT NULL,
    product_photo		NVARCHAR(500),
    total_star			INT DEFAULT 0,
    total_review		INT DEFAULT 0,
    average_rating		DECIMAL(2,1)		DEFAULT 0,
    is_deleted_status	BIT					DEFAULT 0
);

CREATE TABLE purchasing_order (
    purchasing_id		INT					IDENTITY(1,1) PRIMARY KEY,
    product_id			INT					NOT NULL,
    purchasing_quantity INT					NOT NULL,
    purchasing_date		DATETIME2,
    CONSTRAINT FK_purchasing_order_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE orders (
    orderId				INT					IDENTITY(1,1) PRIMARY KEY,
    memberId			INT					NOT NULL,
    priceTotal			INT					NOT NULL,
    transactionTime		DATETIME2(0)		NOT NULL,
    paymentMethod		NVARCHAR(10)		NOT NULL,
    paymentStatus		NVARCHAR(10)		NOT NULL,
    orderStatus			NVARCHAR(10)		NOT NULL,
    pickupMethod		NVARCHAR(10)		NOT NULL,
    trackingNum			NVARCHAR(10)		NULL,
    updateTime			DATETIME2(0)		NOT NULL
);

CREATE TABLE order_detail (
    orderDetailId		INT					IDENTITY(1,1) PRIMARY KEY,
    orderId				INT					NOT NULL,
    productId			INT					NOT NULL,
    quantity			INT					NOT NULL,
    unitPrice			INT					NOT NULL,
    subtotal			INT					NOT NULL,
    rating				INT					NULL,
    comment				NVARCHAR(50)		NULL,
    status				VARCHAR(20)			NULL DEFAULT ('active')
);

ALTER TABLE order_detail
ADD CONSTRAINT FK_order_detail_orders
FOREIGN KEY (orderId)
REFERENCES orders(orderId);