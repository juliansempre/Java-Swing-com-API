import express, { Router } from 'express';

import { addUser, deleteUser, getUsers, updateUser } from "./user.js";

const router = express.Router()

router.get("/", getUsers)

router.post("/", addUser)

router.put("/:id", updateUser)

router.delete("/:id", deleteUser)

export default router;