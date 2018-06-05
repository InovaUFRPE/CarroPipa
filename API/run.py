from app import carropipa
from flask_script import Server

#server = Server(host='192.168.42.87', port=5000)

if __name__ == "__main__":
    carropipa.run(host='192.168.42.87')
