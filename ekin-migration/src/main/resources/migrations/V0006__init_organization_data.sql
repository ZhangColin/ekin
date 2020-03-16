INSERT INTO `sys_organizations`
    (`id`, `parent_id`, `name`, `type`, `code`, `description`, `enabled`, `sort`)
VALUES
    (1377345482606645248, 0, 'Cartisan', 1, 'Cartisan', 'Cartisan', 1, 10);

INSERT INTO `sys_user_organizations`(`user_id`, `organization_id`) VALUES (1265586318612762624, 1377345482606645248);
