<?php
define('HOST', 'mysql.nazuka.net');
define('USER', 'u736810315_sdgp');
define('PASS', 'Semicolon04');
define('DB', 'u736810315_sdgp');

$con = mysqli_connect(HOST, USER, PASS, DB);

$sql = "select * from sdgpdata";

$res = mysqli_query($con, $sql);

$result = array();

while ($row = mysqli_fetch_array($res)) {
    array_push($result,
        array('id' => $row[0],
            'name' => $row[1]
        ));
}

echo json_encode(array("result" => $result));

mysqli_close($con);

?>