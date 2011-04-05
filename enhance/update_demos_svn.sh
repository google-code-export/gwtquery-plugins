#!/bin/sh

# Script to update the demo deployed in svn

V=`grep "<version>" pom.xml | sed -e 's,^.*<version>,,' -e 's,</version>.*$,,' | head -1`
A=`grep "<artifactId>" pom.xml | sed -e 's,^.*<artifactId>,,' -e 's,</artifactId>.*$,,' | head -1`"-sample"

P=`grep "<runTarget>" sample/pom.xml | sed -e 's,^.*<runTarget>,,' -e 's,</runTarget>.*$,,' | head -1 | cut -d "/" -f1`

mvn package -Dmaven.test.skip || exit

mkdir -p demos

cp -r sample/target/$A-$V/Enhance demos/ || exit
cp -r sample/target/$A-$V/EnhanceGwt demos/ || exit
cp -r sample/target/$A-$V/index.html demos/ || exit

for i in `find demos -type d | grep -v .svn | sed -e 's,^demos/,,g'`
do
   [ ! -d sample/target/$A-$V/$i ] && svn delete demos/$i 
done

for i in `find demos -type f | grep -v .svn | sed -e 's,^demos/,,g'`
do
   [ ! -f sample/target/$A-$V/$i ] && svn delete demos/$i 
done

find demos  | grep -v .svn | xargs svn add

find demos -type f -name "*html" -exec svn propset svn:mime-type text/html '{}' ';'
find demos -type f -name "*js" -exec svn propset svn:mime-type text/javascript '{}' ';'
find demos -type f -name "*css" -exec svn propset svn:mime-type text/css '{}' ';'
