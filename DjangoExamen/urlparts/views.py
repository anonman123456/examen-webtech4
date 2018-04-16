from django.shortcuts import render
from django.http import HttpResponse
from urllib.parse import urlparse
# Create your views here.
def doMagic(request):
    return HttpResponse("Hello World!")