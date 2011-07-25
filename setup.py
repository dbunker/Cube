# simple script to build the cube from source
import os,sys

if len(sys.argv) > 1:
	if sys.argv[1] == 'clean':
		os.system('rm Cube.jar')
		os.system('rm -r com')
		os.system('rm depend.png')
	elif sys.argv[1] == 'graph':
		os.system('dot depend.dot -Tpng -o depend.png')
	elif sys.argv[1] == 'build':
		res = os.system('javac -d . *.java')
		if res != 0:
			exit()
		os.system('jar -cvf Cube.jar com/gen/cube/*.class')
	else:
                print 'Unrecognized command.'
else:
	print "No command."
