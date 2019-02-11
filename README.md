# GOT COLLECTION
Projeto de conclusão do curso Android Developer Nanodegree, da Udacity

Descrição
---------
A saga Game of Thrones possui uma infinidade de personagens, culturas e informações; sendo um universo fictício extremamente rico. Entretanto, nem sempre é simples lembrar informações básicas de cada personagem, o que pode deixar os fãs meio perdidos no decorrer da história. Este app visa facilitar a obtenção de informações específicas de cada personagem, a fim de prover um guia para consultas rápidas, de forma organizada; e assim gerar uma melhor experiência aos fãs.

Features
--------
A aplicação tem como principais recursos:
  * Procurar um determinado personagem
  * Exibir detalhes, como livros em que ele aparece, casa que pertence ou cultura
  * Salvar personagem na lista de favoritos
  * Listar os personagens favoritados por meio de um widget na Home Screen
  * Permitir acessar rapidamente informações de personagens, ao clicar na listagem do
widget

Screenshots
--------
<table>
 <tr>
  
  <td>
   <img src="https://github.com/J-Henrique/Capstone-Project/blob/master/got-collection/docs/images/characters.png" width="200">
  </td>
  
  <td>
   <img src="https://github.com/J-Henrique/Capstone-Project/blob/master/got-collection/docs/images/favorites.png" width="200">
  </td>
  
  <td>
   <img src="https://github.com/J-Henrique/Capstone-Project/blob/master/got-collection/docs/images/details.png" width="200">
  </td>
  
  <td>
   <img src="https://github.com/J-Henrique/Capstone-Project/blob/master/got-collection/docs/images/widget.png" width="200">
  </td>
  
 </tr>
</table>

Persistência de dados
---------------------
O acesso às informações dos personagens será de duas formas: consumindo uma API ou consultado um banco de dados local.
Somente os personagens favoritados serão persistidos em banco de dados, por meio da biblioteca Room e utilizando o padrão MVVM. Dessa forma, quando o usuário clicar no botão "favoritar" o objeto será gravado em banco, estando disponível tanto pelo App quanto pelo Widget.
O diagrama abaixo demonstra a relação entre as classes e o método de acesso ao banco:
![](https://github.com/J-Henrique/Capstone-Project/blob/master/got-collection/docs/images/MVVM_Diagram.png)

Bibliotecas utilizadas
----------------------
  * [Picasso][1], para carregar imagens da web e caching
  * [Material Design Compatibility][2], utilização de componentes padrões do Material Design
  * [Retrofit][3], consumo de APIs e gerenciamento de requisições
  * [Room][4], persistência de dados em banco de dados local
  * [Parceler][5], para serializar e desserializar objetos compartilhados entre as activities
  * [Circle Image View][6], criação de views de formato circular para conter a imagem do personagem
  * [Gson][7], converter objetos para formato JSON antes de persistir em banco (TypeConverter)

Serviços Firebase
-----------------
  * **Google Analytics para Firebase**, para identificar informações básicas do público que
está utilizando a aplicação e quais as buscas mais realizadas
  * **Google Admob para Firebase**, para exibir anúncios na versão free do app

[1]: https://github.com/square/picasso/
[2]: https://material.io/design/
[3]: https://square.github.io/retrofit/
[4]: https://developer.android.com/topic/libraries/architecture/room
[5]: https://github.com/johncarl81/parceler
[6]: https://github.com/hdodenhof/CircleImageView
[7]: https://github.com/google/gson
