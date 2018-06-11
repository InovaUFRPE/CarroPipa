from flask import render_template, jsonify, request
from flask_sqlalchemy import SQLAlchemy, BaseQuery
from app import app, db

from app.models.tables import Usuario, Pessoa, Endereco, Empresa, Cliente, Caminhao, Motorista, Pedido#, Ranking, FormaPagto, Pagamento

@app.route("/index/<user>")
@app.route("/", defaults={'user': None})
def index(user):
    return render_template('index.html',
                           user=user)


'''----------------------------Usuario--------------------------------'''

@app.route("/usuario/add/<email>,<senha>")
def usuario_add(email,senha):
    i = Usuario(email,senha)
    db.session.add(i)
    db.session.commit()
    return "Usuário \"" + i.email + "\" incluido com sucesso!"

@app.route("/usuario/delete/<id_usuario>")
def usuario_delete(id_usuario):
    d = Usuario.query.get(id_usuario)
    db.session.delete(d)
    db.session.commit()
    return "Usuário \"" + d.email + "\" excluído com sucesso!"

@app.route("/usuario/get/<id_usuario>")
def usuario_get(id_usuario):
    g = Usuario.query.get(id_usuario)
    return "Usuário \"" + g.email

@app.route("/usuario/update/<id_usuario>,<email>,<senha>")
def usuario_update(id_usuario,email,senha):
    u = Usuario.query.get(id_usuario)
    u.email = email
    u.senha = senha
    db.session.commit()
    return "Usuário \"" + u.email + "\" alterado com sucesso!"

'''----------------------------Pessoa--------------------------------'''

@app.route("/pessoa/add/<nomerazaosocial>,<telefone>,<id_usuario>,<tipopessoa>,<cpfcnpj>")
def pessoa_add(nomerazaosocial,telefone,id_usuario,tipopessoa,cpfcnpj):
    i = Pessoa(nomerazaosocial,telefone,id_usuario,tipopessoa,cpfcnpj)
    db.session.add(i)
    db.session.commit()
    return "Pessoa \"" + i.nomerazaosocial + "\" incluida com sucesso!"

@app.route("/pessoa/delete/<id_pessoa>")
def pessoa_delete(id_pessoa):
    d = Pessoa.query.get(id_pessoa)
    db.session.delete(d)
    db.session.commit()
    return "Pessoa \"" + d.nomerazaosocial + "\" excluída com sucesso!"

@app.route("/pessoa/get/<id_pessoa>")
def pessoa_get(id_pessoa):
    g = Pessoa.query.get(id_pessoa)
    return "Pessoa \"" + g.nomerazaosocial

@app.route("/pessoa/update/<id_pessoa>,<nomerazaosocial>,<telefone>,<id_usuario>,<tipopessoa>,<cpfcnpj>")
def pessoa_update(id_pessoa,nomerazaosocial,telefone,id_usuario,tipopessoa,cpfcnpj):
    u = Pessoa.query.get(id_pessoa)
    u.nomerazaosocial = nomerazaosocial
    u.telefone = telefone
    u.id_usuario = id_usuario
    u.tipopessoa = tipopessoa
    u.cpfcnpj = cpfcnpj
    db.session.commit()
    return "Pessoa \"" + u.nomerazaosocial + "\" alterada com sucesso!"

'''----------------------------Endereco--------------------------------'''

@app.route("/endereco/add/<id_pessoa>,<logradouro>,<complemento>,<bairro>,<cidade>,<cep>,<uf>")
def endereco_add(id_pessoa,logradouro,complemento,bairro,cidade,cep,uf):
    i = Endereco(id_pessoa,logradouro,complemento,bairro,cidade,cep,uf)
    db.session.add(i)
    db.session.commit()
    return "Endereco \"" + i.logradouro + "\" incluido com sucesso!"

@app.route("/endereco/delete/<id_endereco>")
def endereco_delete(id_endereco):
    d = Endereco.query.get(id_endereco)
    db.session.delete(d)
    db.session.commit()
    return "Endereco \"" + d.logradouro + "\" excluído com sucesso!"

@app.route("/endereco/get/<id_endereco>")
def endereco_get(id_endereco):
    g = Endereco.query.get(id_endereco)
    return "Endereco \"" + g.logradouro

@app.route("/endereco/update/<id_pessoa>,<logradouro>,<complemento>,<bairro>,<cidade>,<cep>,<uf>")
def endereco_update(id_pessoa,logradouro,complemento,bairro,cidade,cep,uf):
    u = Endereco.query.get(id_endereco)
    u.logradouro = logradouro
    u.complemento = complemento
    u.bairro = bairro
    u.cidade = cidade
    u.cep = cep
    u.uf = uf
    db.session.commit()
    return "Endereco \"" + u.logradouro + "\" alterado com sucesso!"

'''----------------------------Empresa--------------------------------'''

@app.route("/empresa/add/<id_pessoa_emp>")
def empresa_add(id_pessoa_emp):
    i = Empresa(id_pessoa_emp)
    db.session.add(i)
    db.session.commit()
    return "Empresa \"" + str(i.id_pessoa_emp) + "\" incluida com sucesso!"

@app.route("/empresa/delete/<id_pessoa_emp>")
def empresa_delete(id_pessoa_emp):
    d = Empresa.query.get(id_pessoa_emp)
    db.session.delete(d)
    db.session.commit()
    return "Empresa \"" + str(d.id_pessoa_emp) + "\" excluída com sucesso!"

@app.route("/empresa/get/<id_pessoa_emp>")
def empresa_get(id_pessoa_emp):
    g = Empresa.query.get(id_pessoa_emp)
    return "Empresa \"" + str(g.id_pessoa_emp)

'''----------------------------Cliente--------------------------------'''

@app.route("/cliente/add/<id_pessoa>")
def cliente_add(id_pessoa):
    i = Cliente(id_pessoa)
    db.session.add(i)
    db.session.commit()
    return "Cliente \"" + str(i.id_pessoa) + "\" incluida com sucesso!"

@app.route("/cliente/delete/<id_pessoa>")
def cliente_delete(id_pessoa):
    d = Cliente.query.get(id_pessoa)
    db.session.delete(d)
    db.session.commit()
    return "Cliente \"" + str(d.id_pessoa) + "\" excluída com sucesso!"

@app.route("/cliente/get/<id_pessoa>")
def cliente_get(id_pessoa):
    g = Cliente.query.get(id_pessoa)
    return "Cliente \"" + str(g.id_pessoa)

'''----------------------------Caminhao--------------------------------'''

@app.route("/caminhao/add/<placa>,<capacidade>,<modelo>,<id_pessoa_emp>")
def caminhao_add(placa,capacidade,modelo,id_pessoa_emp):
    i = Caminhao(placa,capacidade,modelo,id_pessoa_emp)
    db.session.add(i)
    db.session.commit()
    return "Caminhao \"" + i.placa + "\" incluido com sucesso!"

@app.route("/caminhao/delete/<id_caminhao>")
def caminhao_delete(id_caminhao):
    d = Caminhao.query.get(id_caminhao)
    db.session.delete(d)
    db.session.commit()
    return "Caminhao \"" + d.placa + "\" excluído com sucesso!"

@app.route("/caminhao/get/<id_caminhao>")
def caminhao_get(id_caminhao):
    g = Caminhao.query.get(id_caminhao)
    return "Caminhao \"" + g.placa

@app.route("/caminhao/update/<id_caminhao>,<placa>,<capacidade>,<modelo>,<id_pessoa_emp>")
def caminhao_update(id_caminhao,placa,capacidade,modelo,id_pessoa_emp):
    u = Caminhao.query.get(id_caminhao)
    u.placa = placa
    u.capacidade = capacidade
    u.modelo = modelo
    u.id_pessoa_emp = id_pessoa_emp
    db.session.commit()
    return "Caminhao \"" + u.placa + "\" alterado com sucesso!"

'''----------------------------Motorista--------------------------------'''

@app.route("/motorista/add/<id_pessoa>,<id_caminhao>,<id_pessoa_emp>")
def motorista_add(id_pessoa,id_caminhao,id_pessoa_emp):
    i = Motorista(id_pessoa,id_caminhao,id_pessoa_emp)
    db.session.add(i)
    db.session.commit()
    return "Motorista \"" + str(i.id_pessoa) + "\" incluido com sucesso!"

@app.route("/motorista/delete/<id_pessoa>")
def motorista_delete(id_pessoa):
    d = Motorista.query.get(id_pessoa)
    db.session.delete(d)
    db.session.commit()
    return "Motorista \"" + str(d.id_pessoa) + "\" excluído com sucesso!"

@app.route("/motorista/get/<id_pessoa>")
def motorista_get(id_pessoa):
    g = Motorista.query.get(id_pessoa)
    return "Motorista \"" + str(g.id_pessoa)

@app.route("/motorista/update/<id_pessoa>,<id_caminhao>,<id_pessoa_emp>")
def motorista_update(id_pessoa,id_caminhao,id_pessoa_emp):
    u = Motorista.query.get(id_pessoa)
    u.id_caminhao = id_caminhao
    u.id_pessoa_emp = id_pessoa_emp
    db.session.commit()
    return "Motorista \"" + str(u.id_pessoa) + "\" alterado com sucesso!"

'''----------------------------Pedido--------------------------------'''

@app.route("/pedido/add/<id_pessoa_cli>,<id_pessoa_mot>,<valor>,<datahora>,<checkin>,<imediatoprogramado>,<confirmaprogramado>")
def pedido_add(id_pessoa_cli,id_pessoa_mot,valor,datahora,checkin,imediatoprogramado,confirmaprogramado):
    i = Pedido(id_pessoa_cli,id_pessoa_mot,valor,datahora,checkin,imediatoprogramado,confirmaprogramado)
    db.session.add(i)
    db.session.commit()
    return "Pedido \"" + str(i.id_pedido) + "\" incluido com sucesso!"

@app.route("/pedido/delete/<id_pedido>")
def pedido_delete(id_pedido):
    d = Pedido.query.get(id_pedido)
    db.session.delete(d)
    db.session.commit()
    return "Pedido \"" + str(d.id_pedido) + "\" excluído com sucesso!"

@app.route("/pedido/get/<id_pedido>")
def pedido_get(id_pedido):
    g = Pedido.query.get(id_pedido)
    return "Pedido \"" + str(g.id_pedido)

@app.route("/pedido/update/<id_pedido>,<id_pessoa_cli>,<id_pessoa_mot>,<valor>,<datahora>,<checkin>,<imediatoprogramado>,<confirmaprogramado>")
def pedido_update(id_pedido,id_pessoa_cli,id_pessoa_mot,valor,datahora,checkin,imediatoprogramado,confirmaprogramado):
    u = Pedido.query.get(id_pedido)
    u.id_pessoa_cli = id_pessoa_cli
    u.id_pessoa_mot = id_pessoa_mot
    u.valor = valor
    u.datahora = datahora
    u.checkin = checkin
    u.imediatoprogramado = imediatoprogramado
    u.confirmaprogramado = confirmaprogramado
    db.session.commit()
    return "Pedido \"" + str(u.id_pedido) + "\" alterado com sucesso!"

#@app.route("/", methods=['GET', 'POST'])
#def index():
#    if (request.method == 'POST'):
#        some_json = request.get_json()
#        return jsonify({'you sent': some_json}), 201
#    else:
#        return jsonify({'abaut': "Hello World!"})

#@app.route("/multi/<int:num>", methods=['GET'])
#def get_multiply10(num):
#    return jsonify({'result': num*10})

#@app.route("/usuario/post/<info>", methods=['GET','POST'])
#@app.route("/usuario/post", defaults={"info":None})
#@app.route("/usuario/post", methods=['GET','POST'])
#def usuario_post():
#    i = Usuario("nanotrigueiro@hotmail.com","1234")
#    db.session.add(i)
#    db.session.commit
#    return "ok"

#@app.route("/teste/", methods=['GET'])
#def teste():
#    return "Oi!"


# @app.route("/test", defaults={'name': None})
# @app.route("/test/<name>")
# def test(name):
#     if name:
#         return "Olá, %s!" % name
#     else:
#         return "Olá, usuário!"
    
    