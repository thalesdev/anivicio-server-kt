CREATE DATABASE IF NOT EXISTS `anivicio` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `anivicio`;

CREATE TABLE IF NOT EXISTS `users` (
    `id` varchar(36) NOT NULL,
    `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password_salt` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `username` varchar(16) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT NOW(),
    `updated_at` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_idx_email` (`email`),
    UNIQUE KEY `u_idx_username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `media_item`(
    `id`          varchar(36)  NOT NULL,
    `owner_id`     varchar(36)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `url` varchar(255) NOT NULL,
    `size` BIGINT NOT NULL,
    `description` MEDIUMTEXT   NOT NULL,
    `media_type`  varchar(255) NOT NULL,
    `created_at`  datetime     NOT NULL default NOW(),
    `updated_at`  datetime     NOT NULL default NOW() on update NOW(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_idx_email` (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_media_type` (`media_type`),
    KEY `idx_owner_id` (`owner_id`),
    CONSTRAINT `fk_media_item_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `show`
(
    `id`           varchar(36)  NOT NULL,
    `name`         varchar(255) NOT NULL,
    `description`  MEDIUMTEXT   NOT NULL,
    `show_type`    varchar(255) NOT NULL,
    `release_date` date         NOT NULL,
    `created_at`   datetime     NOT NULL default NOW(),
    `updated_at`   datetime     NOT NULL default NOW() on update NOW(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_idx_email` (`id`),
    KEY `idx_name` (`name`),
    KEY `idx_show_type` (`show_type`)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4  COLLATE = utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `show_media` (
    `show_id` varchar(36) NOT NULL,
    `media_id` varchar(36) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT NOW(),
    `updated_at` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (`show_id`, `media_id`),
    CONSTRAINT `fk_show_media_show_id` FOREIGN KEY (`show_id`) REFERENCES `show` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_show_media_media_id` FOREIGN KEY (`media_id`) REFERENCES `media_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `show_casting_performer`(
    `id` varchar(36) NOT NULL,
    `name` varchar(255) NOT NULL,
    `media_id` varchar(36) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT NOW(),
    `updated_at` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (`id`),
    KEY `idx_media_id` (`media_id`),
    CONSTRAINT `fk_casting_performer_media_id` FOREIGN KEY (`media_id`) REFERENCES `media_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `show_casting`(
    `id` varchar(36) NOT NULL,
    `show_id` varchar(36) NOT NULL,
    `performer_id` varchar(36) NOT NULL,
    `role` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT NOW(),
    `updated_at` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (`id`),
    KEY `idx_show_id` (`show_id`),
    KEY `idx_performer_id` (`performer_id`),
    CONSTRAINT `fk_show_casting_show_id` FOREIGN KEY (`show_id`) REFERENCES `show` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_show_casting_performer_id` FOREIGN KEY (`performer_id`) REFERENCES `show_casting_performer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `show_genre`(
    `id` varchar(36) NOT NULL,
    `show_id` varchar(36) NOT NULL,
    `genre` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT NOW(),
    `updated_at` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (`id`),
    KEY `idx_show_id` (`show_id`),
    CONSTRAINT `fk_show_genre_show_id` FOREIGN KEY (`show_id`) REFERENCES `show` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;
