<?php
/**
 * Created by PhpStorm.
 * User: Felipe
 * Date: 21/05/2018
 * Time: 10:28
 */

$host = "127.0.0.1";
$user = "root";
$password = "";
$banco = "carropipa";

$email = $_POST['user_mail'];
$senha = $_POST['user_pass'];
$nome = $_POST['user_name'];
$sobrenome = $_POST['user_last'];
$telefone = $_POST['user_fone'];

$dbc = mysqli_connect("$host", "$user", "$password", "$banco")
or die("Erro ao conectar ao banco de dados");
//var_dump($dbc);

$query="INSERT INTO cadastro (nome,sobrenome,email,telefone,senha) VALUES"
."('$nome','$sobrenome','$email','$telefone','$senha')";

//$query = "SELECT * FROM cadastro" or die("Query Error: Erro ao Consultar Banco de Dados");

$result=  mysqli_query($dbc, $query)or die("Query Error - Verifique seu cadastro.<br/><a href=cadastra.php>Voltar</a>");
var_dump($result);
mysqli_close($dbc);

if($result){
    echo "<br/>Client Adicionado com Sucesso!";
    echo "<br/><a href=cadastra.php>Voltar</a>";
}
else{
    echo "<br/>Adição de cliente sem sucesso...";
    echo "<br/><a href=cadastra.php>Voltar</a>";
}

?>