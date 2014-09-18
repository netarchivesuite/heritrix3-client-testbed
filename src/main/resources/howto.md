To create the .jks file:

Browse to heritrix GUI (e.g. in chrome) and export the https certificate to the file "Heritrix Ad-Hoc HTTPS Certificate"
in this directory. Then use keytool to import the certificate as follows:

    keytool -import -alias pc609-heritrix -file Heritrix\ Ad-Hoc\ HTTPS\ Certificate  -keystore test_keystore.jks

When prompted, enter the password "111111".