<html>
<header>
    <h2>Cadastro</h2>
</header>
<nav>
    <form action="cadastrador.php" method="post">
        <div>
            <img src="people.png">
            <input type="text" name="user_name" value="Nome">
            <input type="text" name="user_last" value="SobreNome"><br>
            <img src="email.png">
            <input type="text" name="user_mail" value="Email"><br>
            <img src="phone.png">
            <input type="text" name="user_fone" value="Telefone"><br>
            <img src="password.png">
            <input type="password" name="user_pass" value="Senha"><br>
        </div>
        <footer>
        <input type="submit" value="Criar Conta" name="enviar">
        </footer>
    </form>
</nav>
</html>



<?php
/**
 * Created by PhpStorm.
 * User: Felipe
 * Date: 20/05/2018
 * Time: 23:33
 */