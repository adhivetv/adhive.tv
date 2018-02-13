import pickle
import os
from os import listdir, getcwd
from os.path import join
import configparser

classes = 27
batch = 64
subdivisions = 8

def convert_annotation(classes, batch, subdivisions):
	in_file = open('yolo.cfg')
	config = configparser.ConfigParser()
	
	config_file = open('yolo-%s.cfg'%(classes), "w")
	
	config.add_section("net")
	config.set("net", "batch", str(batch))
	config.set("net", "subdivisions", str(subdivisions))
	config.set("net", "width", "608")
	config.set("net", "height", "608")
	config.set("net", "channels", "3")
	config.set("net", "momentum", "0.9")
	config.set("net", "decay", "0.0005")
	config.set("net", "angle", "0")
	config.set("net", "saturation ", "1.5")
	config.set("net", "exposure ", "1.5")
	config.set("net", "hue", ".1")
	
	config.set("net", "learning_rate", "0.001")
	config.set("net", "burn_in", "1000")
	config.set("net", "max_batches", "500200")
	config.set("net", "policy ", "steps")
	config.set("net", "steps ", "400000,450000")
	config.set("net", "scales", ".1,.1")
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "32")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("maxpool")
	config.set("maxpool", "size", "2")
	config.set("maxpool", "stride", "2")

	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "64")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("maxpool")
	config.set("maxpool", "size", "2")
	config.set("maxpool", "stride", "2")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "128")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "64")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "128")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")

	config.add_section("maxpool")
	config.set("maxpool", "size", "2")
	config.set("maxpool", "stride", "2")
	
	config.write(config_file)
	config = configparser.ConfigParser()

	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "256")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "128")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "256")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")

	config.add_section("maxpool")
	config.set("maxpool", "size", "2")
	config.set("maxpool", "stride", "2")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "512")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "256")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "512")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "256")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "512")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("maxpool")
	config.set("maxpool", "size", "2")
	config.set("maxpool", "stride", "2")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "512")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "512")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("route")
	config.set("route", "layers", "-9")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "64")
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("reorg")
	config.set("reorg", "stride", "2")
	
	config.add_section("route")
	config.set("route", "layers", "-1, -4")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "batch_normalize", "1")
	config.set("convolutional", "filters", "1024")
	config.set("convolutional", "size", "3")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.write(config_file)
	config = configparser.ConfigParser()
	
	config.add_section("convolutional")
	config.set("convolutional", "filters", str((classes+5)*5))
	config.set("convolutional", "size", "1")
	config.set("convolutional", "stride", "1")
	config.set("convolutional", "pad", "1")
	config.set("convolutional", "activation", "leaky")
	
	config.add_section("region")
	config.set("region", "anchors", "0.57273, 0.677385, 1.87446, 2.06253, 3.33843, 5.47434, 7.88282, 3.52778, 9.77052, 9.16828")
	config.set("region", "bias_match", "1")
	config.set("region", "classes", str(classes))
	config.set("region", "coords", "4")
	config.set("region", "num", "5")
	config.set("region", "softmax", "1")
	config.set("region", "jitter", ".3")
	config.set("region", "rescore", "1")
	
	config.set("region", "object_scale", "5")
	config.set("region", "noobject_scale", "1")
	config.set("region", "class_scale", "1")
	config.set("region", "coord_scale", "1")
	
	config.set("region", "absolute", "1")
	config.set("region", "thresh ", ".6")
	config.set("region", "random", "1")
	
	config.write(config_file)
	

convert_annotation(classes, batch, subdivisions)