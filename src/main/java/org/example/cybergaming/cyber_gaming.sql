create database cyber_gaming;
use cyber_gaming;

drop table if exists transactions;
drop table if exists orders;
drop table if exists bookings;
drop table if exists foods;
drop table if exists pcs;
drop table if exists categories;
drop table if exists users;
create table users (
    id int auto_increment primary key,
    username varchar(50) unique not null,
    password varchar(255) not null,
    full_name varchar(100),
    phone varchar(20),
    balance decimal(10, 2) default 0,
    role enum('admin', 'staff', 'customer') not null,
    created_at timestamp default current_timestamp
);
create table categories (
    id int auto_increment primary key,
    name varchar(50) not null
);
insert into categories(name)
values('standard'),('vip'),('stream room');
create table pcs (
    id int auto_increment primary key,
    pc_number varchar(20),
    category_id int,
    configuration text,
    price_per_hour decimal(10, 2),
    status enum('available', 'in_use', 'maintenance') default 'available',
    foreign key (category_id) references categories(id)
);
create table foods (
    id int auto_increment primary key,
    name varchar(100),
    description text,
    price decimal(10, 2),
    stock int default 0
);
create table bookings (
    id int auto_increment primary key,
    user_id int,
    pc_id int,
    start_time datetime,
    end_time datetime,
    status enum('pending', 'confirmed', 'serving', 'completed') default 'pending',
    total_cost decimal(10, 2) default 0,
    foreign key (user_id) references users(id),
    foreign key (pc_id) references pcs(id)
);
create index idx_bookings_pc_time on bookings(pc_id, start_time, end_time, status);
create table orders (
    id int auto_increment primary key,
    booking_id int,
    food_id int,
    quantity int,
    total_price decimal(10, 2),
    status enum('pending', 'preparing', 'done') default 'pending',
    foreign key (booking_id) references bookings(id),
    foreign key (food_id) references foods(id)
);
create index idx_orders_booking on orders(booking_id);
-- Bảng transactions cho ví điện tử
create table transactions (
    id int auto_increment primary key,
    user_id int not null,
    type enum('TOPUP', 'PAYMENT', 'REFUND') not null,
    amount decimal(15, 2) not null,
    description text,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references users(id)
);
-- Index cho tìm kiếm nhanh
create index idx_transactions_user_id on transactions(user_id);
create index idx_transactions_created_at on transactions(created_at);

insert into users(username,password,full_name,phone,balance,role)
values
('admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','Admin','0900000001',0,'admin'),
('staff1','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','Nhân viên 1','0900000002',0,'staff'),
('customer1','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','Khách 1','0900000003',100000,'customer'),
('customer2','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','Khách 2','0900000004',100000,'customer');
-- Insert sample PCs
insert into pcs(
        pc_number,
        category_id,
        configuration,
        price_per_hour,
        status
    )
values ('PC001',1,'Intel i5-10400, 16GB RAM, GTX 1660, 24" Monitor',25000.00,'available'),
    ('PC002',1,'Intel i5-10400, 16GB RAM, GTX 1660, 24" Monitor',25000.00,'available'),
    ('PC003',1,'Intel i5-10400, 16GB RAM, GTX 1660, 24" Monitor',25000.00,'in_use'),
    ('PC004',2,'Intel i7-10700, 32GB RAM, RTX 3070, 27" 144Hz Monitor',45000.00,'available');
-- Insert sample foods
insert into foods(name, description, price, stock)
values ('Coca Cola','Nước ngọt Coca Cola 390ml',15000.00,50),
    ('Pepsi', 'Nước ngọt Pepsi 390ml', 15000.00, 45),
    ('Mì Gói Hảo Hảo','Mì gói Hảo Hảo tôm chua cay',10000.00,30),
    ('Cà phê sữa đá','Cà phê Việt Nam sữa đá',20000.00,40),
    ('Trà chanh', 'Trà chanh đá', 18000.00, 35);
-- Insert sample bookings (for testing double booking prevention)
insert into bookings(user_id,pc_id,start_time,end_time,status,total_cost)
values (3,1,'2024-03-28 09:00:00','2024-03-28 11:00:00','confirmed',50000.00),
    (4,2,'2024-03-28 10:00:00','2024-03-28 12:00:00','confirmed',50000.00);
-- Insert sample orders
insert into orders(booking_id, food_id, quantity, total_price, status)
values
(1,1,2,30000,'done'),
(2,4,1,20000,'pending');
select *from users;
select *from categories;
select *from pcs;
select *from foods;
select *from bookings;
select *from orders;