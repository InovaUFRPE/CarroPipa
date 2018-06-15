from app import db


class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String, unique=True)
    password = db.Column(db.String)


    @property
    def is_authenticated(self):
        return True

    @property
    def is_active(self):
        return True

    @property
    def is_anonymous(self):
        return False

    def get_id(self):
        return self.id

    def __init__(self, username, password):
        self.username = username
        self.password = password


    def __repr__(self):
        return '<User %r>' % self.username


class Pessoa(db.Model):

    __tablename__ = 'pessoas'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    lastname = db.Column(db.String)
    telefone = db.Column(db.String)
    email = db.Column(db.String, unique=True)
    rank = db.Column(db.Float)
    user_id = db.Column(db.Integer, db.ForeignKey('users.id'))

    user = db.relationship('User', foreign_keys=user_id)


    def __init__(self, name, lastname, email, telefone, rank, user_id):
        self.name = name
        self.lastname = lastname
        self.email = email
        self.telefone = telefone
        self.rank = rank
        self.user_id = user_id

    def __repr__(self):
        return '<User %r rate: %f>' % (self.name+" "+self.lastname, self.rank)
