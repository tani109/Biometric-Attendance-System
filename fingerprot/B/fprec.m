clear;
clc;
close all;
global immagine n_bands h_bands n_arcs h_radius h_lato n_sectors matrice matricer num_disk


n_bands = 4;
h_bands = 20;
n_arcs = 16;
h_radius = 12;
h_lato = h_radius + ( n_bands * h_bands * 2 ) + 16;
if mod( h_lato, 2 ) == 0
h_lato = h_lato - 1;
end 
n_sectors = n_bands * n_arcs;
num_disk = 8;

matrice = zeros( h_lato );
matricer = zeros( h_lato );
for ii = 1:( h_lato * h_lato )
matrice( ii ) = whichsector( ii, 0 );
matricer( ii ) = whichsector( ii, 1 );
end 



chos = 0;
possibility = 5;

messaggio = 'Insert the number of set: each set determins a class. This set should include a number of images for each person, with some variations in expression and in the lighting.';

while chos ~= possibility, 
chos = menu( 'Fingerprint Recognition System', 'train single image', 'Train multiple images', 'Match single image', 'Match multiple images',  ...
 'Exit' );

if chos == 1
clc;
close all;
selezionato = 0;

[ namefile, pathname ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image' );
if namefile ~= 0
[ img, map ] = imread( strcat( pathname, namefile ) );
selezionato = 1;
else 
disp( 'Select a grayscale image' );
end 
if ( any( namefile ~= 0 ) && length( size( img ) ) > 2 )
disp( 'Select a grayscale image' );
selezionato = 0;
end 

if selezionato == 1


immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ YofCenter, XofCenter ] = supercore7( fingerprint );
[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code1{ angle + 1 } = vector( 1:n_sectors );
end 













[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 1 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 1 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 1 );
finger_code2{ angle + 1 } = vector( 1:n_sectors );
end 

if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );
fp_number = fp_number + 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile;
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector', '-append' );
else 
fp_number = 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile;
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector' );
end 

message = strcat( 'FingerCode was succesfully added to database. Fingerprint no. ', num2str( fp_number ) );
msgbox( message, 'FingerCode DataBase', 'help' );
end 
end 



if chos == 2
clc;
close all;
selezionato = 0;

[ namefile, pathname, filterindex ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image','MultiSelect','on' );
numfiles = size(namefile,2);
for ii = 1:numfiles
disp([ namefile{ii}, pathname ]);


   if namefile{ii} ~= 0
    [ img, map ] = imread( strcat( pathname, namefile{ii} ) );
    selezionato = 1;
    else 
    disp( 'Select a grayscale image' );
    end 
    if ( any( namefile{ii} ~= 0 ) && length( size( img ) ) > 2 )
    disp( 'Select a grayscale image' );
    selezionato = 0;
    end 

if selezionato == 1


immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ YofCenter, XofCenter ] = supercore7( fingerprint );
[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code1{ angle + 1 } = vector( 1:n_sectors );
end 







[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 1 );

for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 1 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 1 );
finger_code2{ angle + 1 } = vector( 1:n_sectors );
end 

if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );
fp_number = fp_number + 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile{ii};
path_vector{ fp_number } = pathname;

save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector','-append' );

else 
fp_number = 1;
data{ fp_number, 1 } = finger_code1;
data{ fp_number, 2 } = finger_code2;
namefile_vector{ fp_number } = namefile{ii};
path_vector{ fp_number } = pathname;
save( 'fp_database.dat', 'data', 'fp_number', 'namefile_vector', 'path_vector' );
end 
end
message = strcat( 'FingerCode was succesfully added to database. Fingerprint no. ', num2str( fp_number ) );
msgbox( message, 'FingerCode DataBase', 'help' );
end 
end 


if chos == 3
clc;
close all;
if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );

selezionato = 0;
[ namefile, pathname ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image' );
if namefile ~= 0
[ img, map ] = imread( strcat( pathname, namefile ) );
selezionato = 1;
else 
disp( 'Select a grayscale image' );
end 
if ( any( namefile ~= 0 ) && length( size( img ) ) > 2 )
disp( 'Select a grayscale image' );
selezionato = 0;
end 

if selezionato == 1

message = strcat( 'Image selected for fingerprint matching: ', namefile );
disp( message );
message = strcat( 'Location: ', pathname );
disp( message );

immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ list_of_core ] = supercore7_list( fingerprint );
results_img = zeros( size( list_of_core, 1 ), 1 );
results_dis = zeros( size( list_of_core, 1 ), 1 );
message = strcat( 'Candidates for core points: ', num2str( size( list_of_core, 1 ) ) );
disp( message );
for scan_core = 1:size( list_of_core, 1 )
message = strcat( 'Scanning candidate # ', num2str( scan_core ) );
disp( message );

YofCenter = list_of_core( scan_core, 1 );
XofCenter = list_of_core( scan_core, 2 );



[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );


vettore_in = zeros( num_disk * n_sectors, 1 );
for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code{ angle + 1 } = vector( 1:n_sectors );
vettore_in( angle * n_sectors + 1:( angle + 1 ) * n_sectors ) = finger_code{ angle + 1 };
end 





vettore_a = zeros( num_disk * n_sectors, 1 );
vettore_b = zeros( num_disk * n_sectors, 1 );
best_matching = zeros( fp_number, 1 );
valori_rotazione = zeros( n_arcs, 1 );

for scanning = 1:fp_number
fcode1 = data{ scanning, 1 };
fcode2 = data{ scanning, 2 };
for rotazione = 0:( n_arcs - 1 )
p1 = fcode1;
p2 = fcode2;

for conta_disco = 1:num_disk
disco1 = p1{ conta_disco };
disco2 = p2{ conta_disco };
for old_pos = 1:n_arcs
new_pos = mod( old_pos + rotazione, n_arcs );
if new_pos == 0
new_pos = n_arcs;
end 
for conta_bande = 0:1:( n_bands - 1 )
disco1r( new_pos + conta_bande * n_arcs ) = disco1( old_pos + conta_bande * n_arcs );
disco2r( new_pos + conta_bande * n_arcs ) = disco2( old_pos + conta_bande * n_arcs );
end 
end 
p1{ conta_disco } = disco1r;
p2{ conta_disco } = disco2r;
end 

for old_disk = 1:num_disk
new_disk = mod( old_disk + rotazione, num_disk );
if new_disk == 0
new_disk = num_disk;
end 
pos = old_disk - 1;
vettore_a( pos * n_sectors + 1:( pos + 1 ) * n_sectors ) = p1{ new_disk };
vettore_b( pos * n_sectors + 1:( pos + 1 ) * n_sectors ) = p2{ new_disk };
end 
d1 = norm( vettore_a - vettore_in );
d2 = norm( vettore_b - vettore_in );
if d1 < d2
val_minimo = d1;
else 
val_minimo = d2;
end 
valori_rotazione( rotazione + 1 ) = val_minimo;
end 
[ minimo, posizione_minimo ] = min( valori_rotazione );
best_matching( scanning ) = minimo;
end 
[ distanza_minima, posizione_minimo ] = min( best_matching );
results_img( scan_core ) = posizione_minimo;
results_dis( scan_core ) = distanza_minima;


end 


[ distanza_minima, posizione_minimo ] = min( results_dis );
dito_minimo = results_img( posizione_minimo );

message = strcat( 'Recognized fingerprint:', namefile_vector{ dito_minimo } );
disp( message );
message = strcat( 'Location:', path_vector{ dito_minimo } );
disp( message );


message = strcat( 'The nearest fingerprint present in DataBase which matchs input fingerprint is  : ', num2str( dito_minimo ),  ...
' with a distance of : ', num2str( distanza_minima ) );
msgbox( message, 'DataBase Info', 'help' );
end 
else 
message = 'DataBase is empty. No check is possible.';
msgbox( message, 'FingerCode DataBase Error', 'warn' );
end 



end 


if chos == 4
clc;
close all;
if ( exist( 'fp_database.dat' ) == 2 )
load( 'fp_database.dat', '-mat' );

selezionato = 0;
[ namefile, pathname, filterindex ] = uigetfile( { '*.bmp;*.tif;*.tiff;*.jpg;*.jpeg;*.gif;*.pgm', 'IMAGE Files (*.bmp,*.tif,*.tiff,*.jpg,*.jpeg,*.gif,*.pgm)' }, 'Chose GrayScale Image','MultiSelect','on' );
numfiles = size(namefile,2);
for ii = 1:numfiles
disp(strcat('Selected: ', namefile{ii},' is selected for matching' ));
%disp([ namefile{ii}, pathname ]);


if namefile{ii} ~= 0
[ img, map ] = imread( strcat( pathname, namefile{ii} ) );
selezionato = 1;
else 
disp( 'Select a grayscale image' );
end 
if ( any( namefile{ii} ~= 0 ) && length( size( img ) ) > 2 )
disp( 'Select a grayscale image' );
selezionato = 0;
end 

if selezionato == 1

message = strcat( 'Image selected for fingerprint matching: ', namefile{ii} );
%disp( message );
message = strcat( 'Location: ', pathname );
%disp( message );

immagine = double( img );

if isa( img, 'uint8' )
graylevmax = 2 ^ 8 - 1;
end 
if isa( img, 'uint16' )
graylevmax = 2 ^ 16 - 1;
end 
if isa( img, 'uint32' )
graylevmax = 2 ^ 32 - 1;
end 
fingerprint = immagine;

N = h_lato;

[ oimg, fimg, bwimg, eimg, enhimg ] = fft_enhance_cubs( fingerprint );
fingerprint = enhimg;
[ list_of_core ] = supercore7_list( fingerprint );
results_img = zeros( size( list_of_core, 1 ), 1 );
results_dis = zeros( size( list_of_core, 1 ), 1 );
message = strcat( 'Candidates for core points: ', num2str( size( list_of_core, 1 ) ) );
%disp( message );
for scan_core = 1:size( list_of_core, 1 )
message = strcat( 'Scanning candidate # ', num2str( scan_core ) );
%disp( message );

YofCenter = list_of_core( scan_core, 1 );
XofCenter = list_of_core( scan_core, 2 );



[ CroppedPrint ] = cropping( XofCenter, YofCenter, fingerprint );
[ NormalizedPrint, vector ] = sector_norm( CroppedPrint, 0, 0 );


vettore_in = zeros( num_disk * n_sectors, 1 );
for ( angle = 0:1:num_disk - 1 )
gabor = gabor2d_sub( angle, num_disk, 0 );
ComponentPrint = conv2fft( NormalizedPrint, gabor, 'same' );
[ disk, vector ] = sector_norm( ComponentPrint, 1, 0 );
finger_code{ angle + 1 } = vector( 1:n_sectors );
vettore_in( angle * n_sectors + 1:( angle + 1 ) * n_sectors ) = finger_code{ angle + 1 };
end 





vettore_a = zeros( num_disk * n_sectors, 1 );
vettore_b = zeros( num_disk * n_sectors, 1 );
best_matching = zeros( fp_number, 1 );
valori_rotazione = zeros( n_arcs, 1 );

for scanning = 1:fp_number
fcode1 = data{ scanning, 1 };
%disp('hello');
%disp(data{scanning});
fcode2 = data{ scanning, 2 };
for rotazione = 0:( n_arcs - 1 )
p1 = fcode1;
p2 = fcode2;

for conta_disco = 1:num_disk
disco1 = p1{ conta_disco };
disco2 = p2{ conta_disco };
for old_pos = 1:n_arcs
new_pos = mod( old_pos + rotazione, n_arcs );
if new_pos == 0
new_pos = n_arcs;
end 
for conta_bande = 0:1:( n_bands - 1 )
disco1r( new_pos + conta_bande * n_arcs ) = disco1( old_pos + conta_bande * n_arcs );
disco2r( new_pos + conta_bande * n_arcs ) = disco2( old_pos + conta_bande * n_arcs );
end 
end 
p1{ conta_disco } = disco1r;
p2{ conta_disco } = disco2r;
end 

for old_disk = 1:num_disk
new_disk = mod( old_disk + rotazione, num_disk );
if new_disk == 0
new_disk = num_disk;
end 
pos = old_disk - 1;
vettore_a( pos * n_sectors + 1:( pos + 1 ) * n_sectors ) = p1{ new_disk };
vettore_b( pos * n_sectors + 1:( pos + 1 ) * n_sectors ) = p2{ new_disk };
end 
d1 = norm( vettore_a - vettore_in );
d2 = norm( vettore_b - vettore_in );
if d1 < d2
val_minimo = d1;
else 
val_minimo = d2;
end 
valori_rotazione( rotazione + 1 ) = val_minimo;
end 
[ minimo, posizione_minimo ] = min( valori_rotazione );
best_matching( scanning ) = minimo;
end 
[ distanza_minima, posizione_minimo ] = min( best_matching );

results_img( scan_core ) = posizione_minimo;
results_dis( scan_core ) = distanza_minima;


end 


[ distanza_minima, posizione_minimo ] = min( results_dis );
dito_minimo = results_img( posizione_minimo );
%disp(dito_minimo);

message = strcat( 'Recognized fingerprint:',newline, namefile_vector{ dito_minimo } ,newline);
%disp( message );

disp(strcat('Recognized:',namefile_vector{ dito_minimo }));
message = strcat( 'Location:', path_vector{ dito_minimo } );
%disp( message );


message = strcat( 'The nearest fingerprint present in DataBase which matchs input fingerprint is  : ', namefile_vector( dito_minimo ),  ...
' with a distance of : ', num2str( distanza_minima ) );
%msgbox( message, 'DataBase Info', 'help' );
%disp(message);
%disp(namefile_vector{ dito_minimo });
end 
end
else 
message = 'DataBase is empty. No check is possible.';
msgbox( message, 'FingerCode DataBase Error', 'warn' );
end 





end 

if chos == 3
clc;
close all;
helpwin softwareinfo;
end 
if chos == 4
clc;
close all;
if ( exist( 'fp_database.dat' ) == 2 )
button = questdlg( 'Do you really want to remove the Database?' );
if strcmp( button, 'Yes' )
delete( 'fp_database.dat' );
msgbox( 'Database was succesfully removed from the current directory.', 'Database removed', 'help' );
end 
else 
warndlg( 'Database is empty.', ' Warning ' )
end 
end 

 
end 
% Decoded using De-pcode utility v1.1 from file /tmp/tmpTLQl9T.p.
% Please follow local copyright laws when handling this file.

