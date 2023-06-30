import express from 'express';
import cors from 'cors';
import userRoutes from "./controller/rota.js";

const app = express();

app.use(express.json()); //modo json para posts
app.use(cors());

//view
app.set('view engine', 'ejs');
app.use(express.static('view'));

app.get('/', function (req, res) {
	res.render('/index');
  });

//link que retorna o json da api
app.use("/api", userRoutes)

app.listen(8800, () => {
	console.log("Servidor iniciado na porta 8800: http://localhost:8800");
});
