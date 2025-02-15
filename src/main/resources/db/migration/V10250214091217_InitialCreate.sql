CREATE TABLE `Whiteboard` (
  `id` INTEGER PRIMARY KEY,
  `name` VARCHAR(255),
  `description` VARCHAR(255),
  `createdAt` DATETIME
);

CREATE TABLE `TaskList` (
  `id` INTEGER PRIMARY KEY,
  `name` VARCHAR(255),
  `description` VARCHAR(255),
  `createdAt` DATETIME,
  `whiteboardId` INTEGER,
  FOREIGN KEY (`whiteboardId`) REFERENCES `Whiteboard` (`id`)
);

CREATE TABLE `Task` (
  `id` INTEGER PRIMARY KEY,
  `name` VARCHAR(255),
  `priorityId` ENUM('HIGH', 'MEDIUM', 'LOW'),
  `description` VARCHAR(255),
  `deadline` DATETIME,
  `createdAt` DATETIME,
  `listId` INTEGER,
  FOREIGN KEY (`listId`) REFERENCES `List` (`id`)
);
