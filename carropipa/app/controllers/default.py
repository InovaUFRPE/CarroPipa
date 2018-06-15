from flask import request
from app import carropipa

from flask import render_template
from flask import flash
from flask_login import login_user
from flask_login import logout_user
from app import db
from app import lm

from app.models.forms import LoginForm
from app.models.forms import CadastrarForm
from app.models.tables import User



@carropipa.route("/")
def index():
    return render_template('index.html')

@carropipa.route("/cadastro/cadastrar", methods=["GET","POST"])
def registrar():
    if request.method=="POST":
        username = request.form.get('username')
        email = request.form.get('email')
        senha = request.form.get('senha')
        i = User(username, password)
        db.session.add(i)
        db.session.commit()
        #recuperar o id que foi inserido no banco
        u = User.query.filter_by(username=username).first()
        if u:
            user_id = u.id
            i = Pessoa(None, None, email, None, None, user_id)
            return "cadastration_ok,{},{}".format(u.username, u.password)
        else:
            return "cadastration_fail"



@carropipa.route("/cadastrar",  methods=["GET","POST"])
def cadastrar():
    formulario = CadastrarForm()
    if formulario.validate_on_submit():
        username = formulario.username.data
        password = formulario.password.data
        i = User(username, password)
        db.session.add(i)
        db.session.commit()
        flash('cadastrado com sucesso')

    return render_template('cadastro.html', form=formulario)


@carropipa.route("/login/logar", methods=['GET', 'POST'])
#@carropipa.route("/login/logar/<info>/", methods=['GET', 'POST'])
#@carropipa.route("/login/logar/", defaults={'info':None}, methods=['GET', 'POST'])
def logar():
    if request.method=='POST':
            login,senha = request.form.get('email'), request.form.get('senha')
            u = User.query.filter_by(username=login).first()
            if u and u.password == senha:
                return "login_ok,{},{}".format(u.username, u.password)
            else:
                return "login_denied"



@carropipa.route("/login", methods=["post","get"])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        u = User.query.filter_by(username=form.username.data).first()
        if u and u.password == form.password.data:
            login_user(u)
        else:
            flash("Invalid Login")
    else:
        print(form.errors)
    return render_template('login.html', form=form)
