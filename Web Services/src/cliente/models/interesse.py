# to use clas as DTO, simplifies jsonification of the class
from dataclasses import dataclass 
from models.enums import *

@dataclass()
class Interesse():
    """ Data Class para registrar de interesse em cotacao"""

    nomeDeUsuario: str
    tipoDaAtualizacao: str
    codigoDaAcao: str
    
    def __init__(self):
        super().__init__()
