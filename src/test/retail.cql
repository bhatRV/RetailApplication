create table if not exists target.product_pricing
(
(id  INTEGER PRIMARY KEY,
currency text,
price integers
);

create index my_idx on product_pricing (id);